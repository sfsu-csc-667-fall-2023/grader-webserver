package tests.webserver667.logging;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import tests.helpers.responses.TestResource;
import webserver667.logging.Logger;
import webserver667.requests.HttpMethods;
import webserver667.requests.HttpRequest;

public class LoggerTest {

  @Test
  public void testGetLogString() throws IOException {

    TestResource resource = new TestResource();
    String testUri = String.format("/%s", resource.getFileName());
    long fileSize = resource.getFileSize();
    String testIp = "127.0.0.1";

    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethods.GET);
    request.setURI(testUri);
    request.setVersion("HTTP/1.1");

    String result = Logger.getLogString(testIp, request, 200, (int) fileSize);

    // Need to figure out how to lock time in test environment, see:
    String expectedStart = String.format("%s - - [", testIp);
    String expectedEnd = String.format("] \"GET %s HTTP/1.1\" 200 %d", testUri, fileSize);

    assertTrue(result.startsWith(expectedStart));
    assertTrue(result.endsWith(expectedEnd));
  }
}
