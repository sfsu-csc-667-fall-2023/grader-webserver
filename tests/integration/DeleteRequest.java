package tests.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import driver.ServerStartup;

public class DeleteRequest {
  @Test
  public void testDeleteRequest() throws IOException, URISyntaxException {
    String testContent = "lhalf9ay0h8y aohg09ahdf 09a0f9 0f9dh a9h ua90auhf 9";
    Path documentRoot = Paths.get(System.getProperty("java.io.tmpdir"));
    Path testPath = Paths.get(documentRoot.toAbsolutePath().toString(), "someDirectory");
    Files.createDirectories(testPath);
    Helper.createTestFile(testPath, "deleteMeFile.png", testContent);

    new Thread() {
      @Override
      public void run() {
        ServerStartup.main(
            new String[] {
                "-p", 9876 + "", "-r", documentRoot.toAbsolutePath().toString()
            });
      }
    }.start();

    URL url = new URI("http://localhost:9876/someDirectory/deleteMeFile.png").toURL();
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("DELETE");
    connection.setConnectTimeout(5000);
    connection.setReadTimeout(5000);

    assertEquals(204, connection.getResponseCode());
    assertEquals("No Content", connection.getResponseMessage());

    File testFile = new File(testPath.toFile(), "deleteMeFile.png");

    assertFalse(testFile.exists());
  }
}
