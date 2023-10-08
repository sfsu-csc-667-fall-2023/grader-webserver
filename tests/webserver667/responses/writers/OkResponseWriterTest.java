package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import tests.helpers.responses.TestOutputStream;
import tests.helpers.responses.TestResource;
import webserver667.exceptions.ServerErrorException;
import webserver667.requests.HttpRequest;
import webserver667.responses.writers.OkResponseWriter;
import webserver667.responses.writers.ResponseWriter;

public class OkResponseWriterTest {
  @Test
  public void testWrite() throws IOException, ServerErrorException {
    String fileContent = "content";

    TestResource testResource = new TestResource();
    testResource.setPath(
        TestResource.createTempResourceFile("fileContent", "fileContent", fileContent));
    TestOutputStream out = new TestOutputStream();

    ResponseWriter writer = new OkResponseWriter(out, testResource, new HttpRequest());
    writer.write();

    String result = out.toString();

    assertTrue(result.startsWith("HTTP/1.1 200 OK\r\n"));
    assertTrue(result.contains("Content-Type: text/html\r\n"));
    assertTrue(result.contains("Content-Length: 7\r\n"));

    byte[] body = out.getBody();
    assertEquals(fileContent.length(), body.length);

    byte[] contentBytes = fileContent.getBytes();
    boolean isEqual = true;
    for (int i = 0; i < body.length; i++) {
      isEqual = isEqual && body[i] == contentBytes[i];
    }
    assertTrue(isEqual);
  }
}
