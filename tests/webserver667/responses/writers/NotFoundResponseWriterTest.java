package tests.webserver667.responses.writers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import tests.dataProviders.ResponseWriterTestProviders;
import webserver667.responses.IResource;
import webserver667.responses.authentication.UserAuthenticator;
import webserver667.responses.writers.NotFoundResponseWriter;
import webserver667.responses.writers.ResponseWriter;

public class NotFoundResponseWriterTest {
  @Test
  public void testWrite() throws IOException {
    IResource testResource = new IResource() {

      @Override
      public boolean exists() {
        return false;
      }

      @Override
      public Path getPath() {
        try {
          return Paths.get(
              Files.createTempDirectory("tmp").toString(),
              Math.floor(Math.random() * 10000) + "_nonexistent_tmp.html");
        } catch (IOException e) {
          return Paths.get(
              "this_really_shouldnt_fail",
              Math.floor(Math.random() * 10000) + "_nonexistent_tmp.html");
        }
      }

      @Override
      public boolean isProtected() {
        return false;
      }

      @Override
      public boolean isScript() {
        return false;
      }

      @Override
      public UserAuthenticator getUserAuthenticator() {
        return null;
      }

      @Override
      public String getMimeType() {
        return "";
      }
    };

    OutputStream out = ResponseWriterTestProviders.createTestOutputStream();

    ResponseWriter writer = new NotFoundResponseWriter(out, testResource);
    writer.write();

    String result = out.toString();

    assertTrue(result.startsWith("HTTP/1.1 404 Not Found\r\n"));
  }
}
