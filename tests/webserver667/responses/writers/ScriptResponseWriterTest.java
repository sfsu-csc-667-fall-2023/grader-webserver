package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

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
    resource.setPath(TestResource.createTempResourceFile("script", ".js", Contents.NODE_SCRIPT_FILE));

    OutputStream out = new TestOutputStream();

    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethods.GET);

    ResponseWriter writer = new ScriptResponseWriter(out, resource, request);
    writer.write();

    String result = out.toString();
    String expectedOutput = String.join("\n",
        "Content-Type: text/html\r\n",
        "Content-Length: 12\r\n",
        "\r\n",
        "Hello world!");

    assertTrue(result.startsWith("HTTP/1.1 200 OK\r\n"));
    assertTrue(result.contains(expectedOutput));
  }
}
