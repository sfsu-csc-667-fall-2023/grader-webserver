package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

import tests.helpers.responses.TestOutputStream;
import tests.helpers.responses.TestResource;
import webserver667.exceptions.ServerErrorException;
import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.writers.MethodNotAllowedResponseWriter;
import webserver667.responses.writers.ResponseWriter;

public class MethodNotAllowedResponseWriterTest {

  @Test
  public void testWrite() throws IOException, ServerErrorException {
    IResource testResource = new TestResource();
    OutputStream out = new TestOutputStream();

    ResponseWriter writer = new MethodNotAllowedResponseWriter(out, testResource, new HttpRequest());
    writer.write();

    String result = out.toString();

    assertTrue(result.startsWith("HTTP/1.1 405 Method Not Allowed\r\n"));
  }

}
