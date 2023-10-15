package tests.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

import driver.ServerStartup;
import gui.ava.html.image.generator.HtmlImageGenerator;

public class ManualTest {
  @Test
  public void testFullWebsite() throws IOException, InterruptedException, URISyntaxException {
    String tarPath = Paths.get(
        System.getProperty("java.io.tmpdir"),
        "public_html.tar").toAbsolutePath().toString();

    Process process = Runtime.getRuntime().exec(new String[] {
        "wget", "-O",
        tarPath,
        "https://raw.githubusercontent.com/sfsu-csc-667-fall-2023/grader-webserver/main/public_html.tar"
    });
    process.waitFor();

    Path documentRoot = Paths.get(
        Paths.get(tarPath).getParent().toAbsolutePath().toString(),
        "public_html");

    process = Runtime.getRuntime().exec(new String[] {
        "tar", "xvf", tarPath });

    process = Runtime.getRuntime().exec(new String[] {
        "mv", "public_html", System.getProperty("java.io.tmpdir"),
    });

    process.waitFor();

    new Thread() {
      @Override
      public void run() {
        ServerStartup.main(
            new String[] {
                "-p", 9876 + "", "-r", documentRoot.toAbsolutePath().toString(),
                "-m", String.join(System.lineSeparator(),
                    List.of(
                        "text/html html", "img/jpeg jpg", "text/javascript js", "text/css css"))
            });
      }
    }.start();

    File f = new File("./manual-test.png");

    HtmlImageGenerator generator = new HtmlImageGenerator();
    generator.loadUrl(
        new URI("http://localhost:9876/index.html").toURL());
    generator.saveAsImage(f.getAbsolutePath());

    assertTrue(f.exists());
  }
}
