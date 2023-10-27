package tests.integration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class Helper {
  public static void createTestFile(Path parent, String filename, String content) throws IOException {
    File contentFile = new File(
        parent.toFile(), filename);

    FileWriter writer = new FileWriter(contentFile);
    writer.write(content);
    writer.flush();
    writer.close();
  }
}
