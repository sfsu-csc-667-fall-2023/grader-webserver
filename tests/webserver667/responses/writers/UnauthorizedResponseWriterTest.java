package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import tests.helpers.responses.CompareTestOutputAndExpectedOutput;
import tests.helpers.responses.TestOutputStream;
import tests.helpers.responses.TestResource;
import webserver667.exceptions.ServerErrorException;
import webserver667.requests.HttpMethods;
import webserver667.requests.HttpRequest;
import webserver667.responses.writers.UnauthorizedResponseWriter;
import webserver667.responses.writers.ResponseWriter;

public class UnauthorizedResponseWriterTest {

  @Test
  public void testWrite() throws IOException, ServerErrorException {
    TestResource testResource = new TestResource();
    testResource.setIsProtected(true);

    TestOutputStream out = new TestOutputStream();

    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethods.GET);

    ResponseWriter writer = new UnauthorizedResponseWriter(out, testResource, request);
    writer.write();

    CompareTestOutputAndExpectedOutput comparator = new CompareTestOutputAndExpectedOutput(out);

    assertTrue(comparator.headContains("HTTP/1.1 401 Unauthorized\r\n".getBytes()));
    assertTrue(comparator.headContains("WWW-Authenticate: Basic realm=\"667 Server\"\r\n".getBytes()));
  }

}
