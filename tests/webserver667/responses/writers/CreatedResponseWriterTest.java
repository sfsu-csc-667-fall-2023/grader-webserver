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
import webserver667.responses.writers.CreatedResponseWriter;
import webserver667.responses.writers.ResponseWriter;

public class CreatedResponseWriterTest {

  @Test
  public void testWrite() throws IOException, ServerErrorException {
    IResource testResource = new TestResource();
    TestOutputStream out = new TestOutputStream();

    ResponseWriter writer = new CreatedResponseWriter(out, testResource, new HttpRequest());
    writer.write();

    CompareTestOutputAndExpectedOutput comparator = new CompareTestOutputAndExpectedOutput(out);

    assertTrue(comparator.headContains("HTTP/1.1 201 Created\r\n".getBytes()));
  }
}
