package tests.webserver667.logging;

import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;

import tests.dataProviders.ResponseWriterTestProviders;
import webserver667.logging.Logger;
import webserver667.requests.HttpMethods;
import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;

public class LoggerTest {

  public void testGetLogString() {

    String testIp = "127.0.0.1";

    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethods.GET);
    request.setURI("/a_resource.html");
    request.setVersion("HTTP/1.1");

    IResource resource = ResponseWriterTestProviders.createTestResource(
        true,
        false,
        false,
        Paths.get(""),
        "text/html");

    String result = Logger.getLogString(testIp, request, resource, 200, 2326);

    assertTrue(result.startsWith("127.0.0.1 - - ["));
    assertTrue(result.endsWith("] \"GET /a_resource.html HTTP/1.1\" 200 2326"));
  }
}
