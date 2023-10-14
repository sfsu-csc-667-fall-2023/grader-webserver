package tests.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import driver.ServerStartup;

public class SimpleRequestTest {
  @Test
  public void testSimpleRequest() throws IOException, URISyntaxException {
    String testContent = "lhalf9ay0h8y aohg09ahdf 09a0f9 0f9dh a9h ua90auhf 9";
    Path documentRoot = Paths.get(System.getProperty("java.io.tmpdir"));

    Helper.createTestFile(documentRoot, "textFile.txt", testContent);

    new Thread() {
      @Override
      public void run() {
        ServerStartup.main(
            new String[] {
                "-p", 9876 + "", "-r", documentRoot.toAbsolutePath().toString()
            });
      }
    }.start();

    URL url = new URI("http://localhost:9876/textFile.txt").toURL();
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.setConnectTimeout(5000);
    connection.setReadTimeout(5000);

    assertEquals(200, connection.getResponseCode());
    assertEquals("OK", connection.getResponseMessage());
    assertEquals("text/text", connection.getHeaderField("Content-Type"));
    assertEquals(testContent.length() + "", connection.getHeaderField("Content-Length"));
  }
}
