package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

import tests.helpers.responses.TestOutputStream;
import tests.helpers.responses.TestResource;
import webserver667.requests.HttpRequest;
import webserver667.responses.writers.NotFoundResponseWriter;
import webserver667.responses.writers.ResponseWriter;

public class NotFoundResponseWriterTest {
  @Test
  public void testWrite() throws IOException {
    TestResource testResource = new TestResource();
    testResource.setExists(false);

    OutputStream out = new TestOutputStream();

    ResponseWriter writer = new NotFoundResponseWriter(out, testResource, new HttpRequest());
    writer.write();

    String result = out.toString();

    assertTrue(result.startsWith("HTTP/1.1 404 Not Found\r\n"));
  }
}
