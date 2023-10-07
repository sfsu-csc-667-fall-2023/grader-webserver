package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

import tests.helpers.responses.TestOutputStream;
import tests.helpers.responses.TestResource;
import webserver667.requests.HttpRequest;
import webserver667.responses.writers.OkResponseWriter;
import webserver667.responses.writers.ResponseWriter;

public class OkResponseWriterTest {
  @Test
  public void testWrite() throws IOException {
    String fileContent = "content";

    TestResource testResource = new TestResource();
    testResource.setPath(
        TestResource.createTempResourceFile("fileContent", "fileContent", fileContent));
    OutputStream out = new TestOutputStream();

    ResponseWriter writer = new OkResponseWriter(out, testResource, new HttpRequest());
    writer.write();

    String result = out.toString();

    assertTrue(result.startsWith("HTTP/1.1 200 OK\r\n"));
    assertTrue(result.contains("Content-Type: text/html\r\n"));
    assertTrue(result.contains("Content-Length: 7\r\n"));
    assertTrue(result.endsWith(fileContent));
  }
}
