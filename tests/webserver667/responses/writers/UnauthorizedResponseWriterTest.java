package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

import tests.helpers.responses.TestOutputStream;
import tests.helpers.responses.TestResource;
import webserver667.requests.HttpMethods;
import webserver667.requests.HttpRequest;
import webserver667.responses.writers.UnauthorizedResponseWriter;
import webserver667.responses.writers.ResponseWriter;

public class UnauthorizedResponseWriterTest {

  @Test
  public void testWrite() throws IOException {
    TestResource testResource = new TestResource();
    testResource.setIsProtected(true);

    OutputStream out = new TestOutputStream();

    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethods.GET);

    ResponseWriter writer = new UnauthorizedResponseWriter(out, testResource, request);
    writer.write();

    String result = out.toString();

    assertTrue(result.startsWith("HTTP/1.1 401 Unauthorized\r\n"));
    assertTrue(result.contains("WWW-Authenticate: Basic realm=\"667 Server\"\r\n"));
  }

}
