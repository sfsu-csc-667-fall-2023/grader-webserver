package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import tests.dataProviders.ResponseWriterTestProviders;
import webserver667.responses.IResource;
import webserver667.responses.writers.ResponseWriter;

public class FileResponseWriterTest {
  @Test
  public void testWrite() throws IOException {
    String fileContent = "content";
    IResource testResource = ResponseWriterTestProviders.createTestResource(fileContent);
    StringBuffer stringBuffer = new StringBuffer();

    OutputStream out = new OutputStream() {
      @Override
      public void write(int b) throws IOException {
        stringBuffer.append((char) b);
      }
    };

    ResponseWriter writer = new ResponseWriter(out, testResource);
    writer.write();

    String result = stringBuffer.toString();

    assertTrue(result.startsWith("HTTP/1.1 200 Ok\r\n"));
    assertTrue(result.contains("Content-Type: text/html\r\n"));
    assertTrue(result.contains("Content-Length: 7\r\n"));
    assertTrue(result.endsWith(fileContent));
  }
}
