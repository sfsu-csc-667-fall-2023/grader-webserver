package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import tests.helpers.responses.TestOutputStream;
import tests.helpers.responses.TestResource;
import tests.helpers.responses.TestUserAuthenticator;
import webserver667.requests.*;
import webserver667.responses.writers.*;

public class ResponseWriterFactoryTest {

  @Test
  public void testCreated() throws IOException {
    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethods.PUT);

    TestResource testResource = new TestResource();
    testResource.setExists(false);

    ResponseWriter writer = ResponseWriterFactory.create(
        new TestOutputStream(),
        testResource,
        request);

    assertInstanceOf(CreatedResponseWriter.class, writer);
  }

  @Test
  public void testForbidden() throws IOException {
    HttpRequest request = new HttpRequest();
    request.addHeader("Authorization: jkasgkjasdkjhksjadhkjasdhkj");

    TestResource testResource = new TestResource();
    testResource.setExists(false);
    testResource.setIsProtected(true);
    testResource.setUserAuthenticator(new TestUserAuthenticator(false));

    ResponseWriter writer = ResponseWriterFactory.create(
        new TestOutputStream(),
        testResource,
        request);

    assertInstanceOf(ForbiddenResponseWriter.class, writer);
  }

  @Test
  public void testNoContent() throws IOException {
    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethods.DELETE);

    TestResource testResource = new TestResource();
    testResource.setExists(true);
    testResource.setPath(Files.createTempFile("doesnt", "matter"));

    ResponseWriter writer = ResponseWriterFactory.create(
        new TestOutputStream(),
        testResource,
        request);

    assertInstanceOf(NoContentResponseWriter.class, writer);
  }

  @Test
  public void testNotFound() throws IOException {
    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethods.GET);

    TestResource testResource = new TestResource();
    testResource.setExists(false);

    ResponseWriter writer = ResponseWriterFactory.create(
        new TestOutputStream(),
        testResource,
        request);

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

    TestResource resource = new TestResource();
    resource.setPath(file.toPath());
    resource.shouldReportModified(false);

    ResponseWriter writer = ResponseWriterFactory.create(
        new TestOutputStream(),
        resource,
        request);

    assertInstanceOf(NotModifiedResponseWriter.class, writer);
  }

  @Test
  public void testOk() throws IOException {
    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethods.GET);

    TestResource resource = new TestResource();

    ResponseWriter writer = ResponseWriterFactory.create(
        new TestOutputStream(), resource, request);

    assertInstanceOf(OkResponseWriter.class, writer);
  }

  @Test
  public void testUnauthorized() throws IOException {
    TestResource resource = new TestResource();
    resource.setExists(false);
    resource.setIsProtected(true);
    resource.setIsScript(true);
    resource.setUserAuthenticator(new TestUserAuthenticator(false));

    ResponseWriter writer = ResponseWriterFactory.create(
        new TestOutputStream(),
        resource,
        new HttpRequest());

    assertInstanceOf(UnauthorizedResponseWriter.class, writer);
  }

  @Test
  public void testScriptResponseWriter() throws IOException {
    TestResource resource = new TestResource();
    resource.setIsScript(true);

    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethods.POST);

    ResponseWriter writer = ResponseWriterFactory.create(
        new TestOutputStream(),
        resource,
        request);

    assertInstanceOf(ScriptResponseWriter.class, writer);
  }
}
