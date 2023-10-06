package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

import tests.dataProviders.ResponseWriterTestProviders;
import webserver667.responses.IResource;
import webserver667.responses.writers.ResponseWriter;
import webserver667.responses.writers.ScriptResponseWriter;

public class ScriptResponseWriterTest {
  @Test
  public void testWrite() throws IOException {
    IResource testResource = ResponseWriterTestProviders.createScriptResource();

    OutputStream out = ResponseWriterTestProviders.createTestOutputStream();

    ResponseWriter writer = new ScriptResponseWriter(out, testResource);
    writer.write();

    String result = out.toString();
    String expectedOutput = String.join("\n",
        "Content-Type: text/html\r\n",
        "Content-Length: ${content.length}\r\n",
        "\r\n",
        "Hello world!");

    assertTrue(result.startsWith("HTTP/1.1 200 OK\r\n"));
    assertTrue(result.contains(expectedOutput));
  }
}
