package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

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

    TestOutputStream out = new TestOutputStream();

    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethods.GET);

    ResponseWriter writer = new ScriptResponseWriter(out, resource, request);
    writer.write();

    String expectedOutput = String.join("",
        "Content-Type: text/html\r\n",
        "Content-Length: 12\r\n",
        "\r\n",
        "Hello world!");

    byte[] body = out.getBody();
    assertEquals(expectedOutput.length(), body.length);

    byte[] contentBytes = expectedOutput.getBytes();
    boolean isEqual = true;
    for (int i = 0; i < body.length; i++) {
      isEqual = isEqual && body[i] == contentBytes[i];
    }
    assertTrue(isEqual);
  }
}
