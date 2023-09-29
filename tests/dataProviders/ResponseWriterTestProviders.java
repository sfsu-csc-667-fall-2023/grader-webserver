package tests.dataProviders;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import webserver667.responses.IResource;

public class ResponseWriterTestProviders {
  public static OutputStream createTestOutputStream() {
    return new OutputStream() {
      StringBuffer buffer = new StringBuffer();

      @Override
      public void write(int b) throws IOException {
        buffer.append((char) b);
      }

      @Override
      public String toString() {
        return this.buffer.toString();
      }
    };
  }

  public static IResource createTestResource(String fileContent) {
    return new IResource() {
      File file;

      @Override
      public boolean exists() {
        return true;
      }

      @Override
      public Path getPath() {
        if (this.file == null) {
          try {
            Path path = Files.createTempFile(null, "index", "html");
            this.file = path.toFile();

            FileWriter writer = new FileWriter(this.file);
            writer.write(fileContent);
            writer.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }

        return this.file.toPath();
      }

      @Override
      public boolean isProtected() {
        return false;
      }

      @Override
      public boolean isScript() {
        return false;
      }
    };
  }

}