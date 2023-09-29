package tests.dataProviders;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import webserver667.responses.IResource;
import webserver667.responses.authentication.UserAuthenticator;

public class ResponseWriterTestProviders {
  public static UserAuthenticator createUserAuthenticator(boolean isAuthenticated) {
    return new UserAuthenticator(null, null) {
      @Override
      public boolean isAuthenticated() {
        return isAuthenticated;
      }
    };
  }

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

  public static IResource createTestResource(
      boolean exists,
      boolean isProtected,
      boolean script,
      Path path) {
    return createTestResource(
        exists,
        isProtected,
        script,
        path,
        createUserAuthenticator(true));
  }

  public static IResource createTestResource(
      boolean exists,
      boolean isProtected,
      boolean script,
      Path path,
      UserAuthenticator authenticator) {
    return new IResource() {

      @Override
      public boolean exists() {
        return exists;
      }

      @Override
      public Path getPath() {
        return path;
      }

      @Override
      public boolean isProtected() {
        return isProtected;
      }

      @Override
      public boolean isScript() {
        return script;
      }

      @Override
      public UserAuthenticator getUserAuthenticator() {
        return authenticator;
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

      @Override
      public UserAuthenticator getUserAuthenticator() {
        return createUserAuthenticator(true);
      }
    };
  }

}
