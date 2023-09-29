package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import webserver667.requests.HttpMethods;
import webserver667.requests.HttpRequest;
import webserver667.responses.writers.CreatedResponseWriter;
import webserver667.responses.writers.ForbiddenResponseWriter;
import webserver667.responses.writers.NoContentResponseWriter;
import webserver667.responses.writers.NotFoundResponseWriter;
import webserver667.responses.writers.NotModifiedResponseWriter;
import webserver667.responses.writers.OkResponseWriter;
import webserver667.responses.writers.ResponseWriter;
import webserver667.responses.writers.ResponseWriterFactory;
import webserver667.responses.writers.ScriptResponseWriter;
import webserver667.responses.writers.UnauthorizedResponseWriter;

import static tests.dataProviders.ResponseWriterTestProviders.*;

public class ResponseWriterFactoryTest {

  @Test
  public void testCreated() {
    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethods.PUT);

    ResponseWriter writer = ResponseWriterFactory.create(
        createTestOutputStream(),
        createTestResource(false, false, false, Paths.get(System.getProperty("java.io.tmpdir"))),
        request);

    assertInstanceOf(CreatedResponseWriter.class, writer);
  }

  @Test
  public void testForbidden() {
    HttpRequest request = new HttpRequest();
    request.addHeader("Authentication: jkasgkjasdkjhksjadhkjasdhkj");

    ResponseWriter writer = ResponseWriterFactory.create(
        createTestOutputStream(),
        createTestResource(
            false, true, false,
            Paths.get(System.getProperty("java.io.tmpdir")),
            createUserAuthenticator(false)),
        request);

    assertInstanceOf(ForbiddenResponseWriter.class, writer);
  }

  @Test
  public void testNoContent() throws IOException {
    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethods.DELETE);

    ResponseWriter writer = ResponseWriterFactory.create(
        createTestOutputStream(),
        createTestResource(
            true, false, false,
            Files.createTempFile("doesnt", "matter")),
        request);

    assertInstanceOf(NoContentResponseWriter.class, writer);
  }

  @Test
  public void testNotFound() {
    ResponseWriter writer = ResponseWriterFactory.create(
        createTestOutputStream(),
        createTestResource(false, false, false,
            Paths.get(System.getProperty("java.io.tmpdir"))),
        new HttpRequest());

    assertInstanceOf(NotFoundResponseWriter.class, writer);
  }

  @Test
  public void testNotModified() throws IOException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss");
    File file = Files.createTempFile("dont", "care").toFile();
    String afterDate = dateFormat.format(LocalDate.now().plusDays(5)) + " GMT";

    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethods.GET);
    request.addHeader(String.format("If-Modified-Since: %s", afterDate));

    ResponseWriter writer = ResponseWriterFactory.create(
        createTestOutputStream(),
        createTestResource(true, false, false, file.toPath()),
        request);

    assertInstanceOf(NotModifiedResponseWriter.class, writer);
  }

  @Test
  public void testOk() {
    ResponseWriter writer = ResponseWriterFactory.create(
        createTestOutputStream(),
        createTestResource(
            true, false, false,
            Paths.get(System.getProperty("java.io.tmpdir"))),
        new HttpRequest());

    assertInstanceOf(OkResponseWriter.class, writer);
  }

  @Test
  public void testUnauthorized() {
    // We need resource where isProtected returns true
    ResponseWriter writer = ResponseWriterFactory.create(
        createTestOutputStream(),
        createTestResource(
            false, true, true,
            Paths.get(System.getProperty("java.io.tmpdir"))),
        new HttpRequest());

    assertInstanceOf(UnauthorizedResponseWriter.class, writer);
  }

  @Test
  public void testScriptResponseWriter() {
    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethods.POST);

    ResponseWriter writer = ResponseWriterFactory.create(
        createTestOutputStream(),
        createTestResource(
            true, false, true,
            Paths.get(System.getProperty("java.io.tmpdir"))),
        request);

    assertInstanceOf(ScriptResponseWriter.class, writer);
  }
}
