package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

import tests.helpers.responses.TestOutputStream;
import tests.helpers.responses.TestResource;
import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.writers.InternalServerErrorResponseWriter;
import webserver667.responses.writers.ResponseWriter;

public class InternalServerErrorResponseWriterTest {

  @Test
  public void testWrite() throws IOException {
    IResource testResource = new TestResource();
    OutputStream out = new TestOutputStream();

    ResponseWriter writer = new InternalServerErrorResponseWriter(out, testResource, new HttpRequest());
    writer.write();

    String result = out.toString();

    assertTrue(result.startsWith("HTTP/1.1 500 Internal Server Error\r\n"));
  }

}
