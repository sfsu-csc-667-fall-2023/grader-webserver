package tests.integration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import webserver667.logging.Logger;

public class Helper {
  public static void createTestFile(Path parent, String filename, String content) throws IOException {
    File contentFile = new File(
        parent.toFile(), filename);

    Logger.debug("Creating test file in " + contentFile.getAbsolutePath());
    FileWriter writer = new FileWriter(contentFile);
    writer.write(content);
    writer.flush();
    writer.close();
  }
}
