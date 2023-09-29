package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

import tests.dataProviders.ResponseWriterTestProviders;
import webserver667.responses.IResource;
import webserver667.responses.writers.OkResponseWriter;
import webserver667.responses.writers.ResponseWriter;

public class OkResponseWriterTest {
  @Test
  public void testWrite() throws IOException {
    String fileContent = "content";
    IResource testResource = ResponseWriterTestProviders.createTestResource(fileContent);

    OutputStream out = ResponseWriterTestProviders.createTestOutputStream();

    ResponseWriter writer = new OkResponseWriter(out, testResource);
    writer.write();

    String result = out.toString();

    assertTrue(result.startsWith("HTTP/1.1 200 OK\r\n"));
    assertTrue(result.contains("Content-Type: text/html\r\n"));
    assertTrue(result.contains("Content-Length: 7\r\n"));
    assertTrue(result.endsWith(fileContent));
  }
}
