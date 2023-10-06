package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import webserver667.requests.*;
import webserver667.responses.writers.*;

import static tests.dataProviders.ResponseWriterTestProviders.*;

public class ResponseWriterFactoryTest {

  @Test
  public void testCreated() {
    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethods.PUT);

    ResponseWriter writer = ResponseWriterFactory.create(
        createTestOutputStream(),
        createTestResource(false,
            false,
            false,
            Paths.get(System.getProperty("java.io.tmpdir")),
            "text/html"),
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
            createUserAuthenticator(false),
            "index/html"),
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
            Files.createTempFile("doesnt", "matter"),
            "index/html"),
        request);

    assertInstanceOf(NoContentResponseWriter.class, writer);
  }

  @Test
  public void testNotFound() {
    ResponseWriter writer = ResponseWriterFactory.create(
        createTestOutputStream(),
        createTestResource(false, false, false,
            Paths.get(System.getProperty("java.io.tmpdir")),
            "text/html"),
        new HttpRequest());

    assertInstanceOf(NotFoundResponseWriter.class, writer);
  }

  @Test
  public void testNotModified() throws IOException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss");
    File file = Files.createTempFile("dont", "care").toFile();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
    }
    String afterDate = dateFormat.format(new Date()) + " GMT";

    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethods.GET);
    request.addHeader(String.format("If-Modified-Since: %s", afterDate));

    ResponseWriter writer = ResponseWriterFactory.create(
        createTestOutputStream(),
        createTestResource(true, false, false, file.toPath(), "text/html"),
        request);

    assertInstanceOf(NotModifiedResponseWriter.class, writer);
  }

  @Test
  public void testOk() {
    ResponseWriter writer = ResponseWriterFactory.create(
        createTestOutputStream(),
        createTestResource(
            true, false, false,
            Paths.get(System.getProperty("java.io.tmpdir")),
            "text/html"),
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
            Paths.get(System.getProperty("java.io.tmpdir")),
            "text/html"),
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
            Paths.get(System.getProperty("java.io.tmpdir")),
            "text/html"),
        request);

    assertInstanceOf(ScriptResponseWriter.class, writer);
  }
}
