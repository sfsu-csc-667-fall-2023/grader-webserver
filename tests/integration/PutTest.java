package tests.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

import driver.ServerStartup;

public class PutTest {
  @Test
  public void testPutRequest() throws IOException, URISyntaxException {
    Path documentRoot = Paths.get(System.getProperty("java.io.tmpdir"));
    String fileContent = "<html><body>Hello world</body></html>";

    new Thread() {
      @Override
      public void run() {
        ServerStartup.main(
            new String[] {
                "-p", 9876 + "", "-r", documentRoot.toAbsolutePath().toString(),
                "-m", "txt text/text"
            });
      }
    }.start();

    String randomDir = (int) Math.floor((Math.random() * 10000000)) + "r";

    URL url = new URI(String.format("http://localhost:9876/%s/testPut.html", randomDir)).toURL();
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("PUT");
    connection.setRequestProperty("Content-Length", fileContent.length() + "");
    connection.setConnectTimeout(5000);
    connection.setReadTimeout(5000);
    connection.setDoOutput(true);
    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
    writer.write(fileContent);
    writer.close();

    assertEquals(201, connection.getResponseCode());
    assertEquals("Created", connection.getResponseMessage());

    File file = Paths.get(
        documentRoot.toAbsolutePath().toString(), randomDir, "testPut.html").toFile();
    assertTrue(file.exists());

    List<String> lines = Files.readAllLines(file.toPath());
    assertTrue(lines.size() == 1);
    assertEquals(fileContent, lines.get(0));
  }
}
