package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;

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

    Path path = TestResource.createTempResourceFile("script", ".js", Contents.NODE_SCRIPT_FILE);
    path.toFile().setExecutable(true);
    resource.setPath(path);

    TestOutputStream out = new TestOutputStream();

    HttpRequest request = new HttpRequest();
    request.setHttpMethod(HttpMethods.GET);

    ResponseWriter writer = new ScriptResponseWriter(out, resource, request);
    writer.write();

    String expectedBody = "Hello world!";
    byte[] expectedHead = String.join("",
        "HTTP/1.1 200 OK\r\n",
        "Content-Type: text/html\r\n",
        "Content-Length: 12\r\n",
        "\r\n").getBytes();

    byte[] head = out.getResponseHead();

    for (int i = 0; i < expectedHead.length; i++) {
      System.out.println(
          String.format("%d %d %d %c, %c", i, expectedHead[i], head[i], (char) expectedHead[i], (char) head[i]));
    }

    assertEquals(expectedHead.length, head.length);

    boolean isHeadEqual = true;
    for (int i = 0; i < expectedHead.length; i++) {
      isHeadEqual = isHeadEqual && head[i] == expectedHead[i];
    }
    assertTrue(isHeadEqual);

    byte[] body = out.getBody();
    assertEquals(expectedBody.length(), body.length);

    byte[] contentBytes = expectedBody.getBytes();
    boolean isEqual = true;
    for (int i = 0; i < body.length; i++) {
      isEqual = isEqual && body[i] == contentBytes[i];
    }
    assertTrue(isEqual);
  }
}
