package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import tests.helpers.responses.CompareTestOutputAndExpectedOutput;
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

    CompareTestOutputAndExpectedOutput comparator = new CompareTestOutputAndExpectedOutput(out);

    assertTrue(comparator.headContains("HTTP/1.1 200 OK\r\n".getBytes()));
    assertTrue(comparator.headContains("Content-Type: text/html\r\n".getBytes()));
    assertTrue(comparator.headContains("Content-Length: 7\r\n".getBytes()));
    assertTrue(comparator.bodyContains(fileContent.getBytes()));
  }
}
