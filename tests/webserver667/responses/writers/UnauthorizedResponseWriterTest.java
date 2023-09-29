package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

import tests.dataProviders.ResponseWriterTestProviders;
import webserver667.responses.IResource;
import webserver667.responses.writers.CreatedResponseWriter;
import webserver667.responses.writers.ResponseWriter;

public class UnauthorizedResponseWriterTest {

  @Test
  public void testWrite() throws IOException {
    IResource testResource = ResponseWriterTestProviders.createTestResource("");
    OutputStream out = ResponseWriterTestProviders.createTestOutputStream();

    ResponseWriter writer = new CreatedResponseWriter(out, testResource);
    writer.write();

    String result = out.toString();

    assertTrue(result.startsWith("HTTP/1.1 401 Unauthorized\r\n"));
    assertTrue(result.contains("WWW-Authenticate: Basic realm=\"667 Server\"\r\n"));
  }

}
