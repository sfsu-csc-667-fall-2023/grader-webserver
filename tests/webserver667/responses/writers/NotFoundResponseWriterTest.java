package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import tests.helpers.responses.CompareTestOutputAndExpectedOutput;
import tests.helpers.responses.TestOutputStream;
import tests.helpers.responses.TestResource;
import webserver667.exceptions.ServerErrorException;
import webserver667.requests.HttpRequest;
import webserver667.responses.writers.NotFoundResponseWriter;
import webserver667.responses.writers.ResponseWriter;

public class NotFoundResponseWriterTest {
  @Test
  public void testWrite() throws IOException, ServerErrorException {
    TestResource testResource = new TestResource();
    testResource.setExists(false);

    TestOutputStream out = new TestOutputStream();

    ResponseWriter writer = new NotFoundResponseWriter(out, testResource, new HttpRequest());
    writer.write();

    CompareTestOutputAndExpectedOutput comparator = new CompareTestOutputAndExpectedOutput(out);

    assertTrue(comparator.headContains("HTTP/1.1 404 Not Found\r\n".getBytes()));
  }
}
