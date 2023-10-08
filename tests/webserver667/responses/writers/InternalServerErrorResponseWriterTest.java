package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import tests.helpers.responses.CompareTestOutputAndExpectedOutput;
import tests.helpers.responses.TestOutputStream;
import tests.helpers.responses.TestResource;
import webserver667.exceptions.ServerErrorException;
import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.writers.InternalServerErrorResponseWriter;
import webserver667.responses.writers.ResponseWriter;

public class InternalServerErrorResponseWriterTest {

  @Test
  public void testWrite() throws IOException, ServerErrorException {
    IResource testResource = new TestResource();
    TestOutputStream out = new TestOutputStream();

    ResponseWriter writer = new InternalServerErrorResponseWriter(out, testResource, new HttpRequest());
    writer.write();

    CompareTestOutputAndExpectedOutput comparator = new CompareTestOutputAndExpectedOutput(out);

    assertTrue(comparator.headContains("HTTP/1.1 500 Internal Server Error\r\n".getBytes()));
  }

}
