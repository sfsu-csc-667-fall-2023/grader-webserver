package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import tests.helpers.responses.CompareTestOutputAndExpectedOutput;
import tests.helpers.responses.Contents;
import tests.helpers.responses.TestOutputStream;
import tests.helpers.responses.TestResource;
import webserver667.exceptions.ServerErrorException;
import webserver667.requests.HttpMethods;
import webserver667.requests.HttpRequest;
import webserver667.responses.writers.ResponseWriter;
import webserver667.responses.writers.ScriptResponseWriter;

public class ScriptResponseWriterTest {
  @Test
  public void testWrite() throws IOException, ServerErrorException {
    TestResource resource = new TestResource();
    resource.setIsScript(true);

    Path path = TestResource.createTempResourceFile("script", ".js", Contents.NODE_SCRIPT_FILE);
    path.toFile().setExecutable(true);
    resource.setPath(path);

    TestOutputStream out = new TestOutputStream();

    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethods.GET);

    ResponseWriter writer = new ScriptResponseWriter(out, resource, request);
    writer.write();

    CompareTestOutputAndExpectedOutput comparator = new CompareTestOutputAndExpectedOutput(out);

    assertTrue(comparator.headContains("HTTP/1.1 200 OK\r\n".getBytes()));
    assertTrue(comparator.headContains("Content-Type: text/html\r\n".getBytes()));
    assertTrue(comparator.headContains("Content-Length: 12\r\n".getBytes()));
    assertTrue(comparator.bodyContains("Hello world!".getBytes()));
  }
}
