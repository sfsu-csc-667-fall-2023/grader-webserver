package tests.webserver667.responses.authentication;

import org.junit.jupiter.api.Test;

import tests.helpers.responses.TestResource;
import webserver667.requests.HttpRequest;
import webserver667.responses.authentication.UserAuthenticator;
import webserver667.responses.authentication.UserPasswordAuthenticator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserAuthenticatorTest {
  private Path createPasswordsFile(String content) throws IOException {
    Path passwordFilePath = Paths.get(System.getProperty("java.io.tmpdir"), ".passwords");

    File file = passwordFilePath.toFile();
    FileWriter writer = new FileWriter(file);
    writer.write(content);
    writer.flush();
    writer.close();

    return passwordFilePath;
  }

  @Test
  public void testIsAuthenticatedWithValidPassword() throws IOException {
    HttpRequest request = new HttpRequest();
    request.addHeader("Authorization: Basic anJvYjpwYXNzd29yZA==");

    Path passwordFilePath = createPasswordsFile("jrob:{SHA-1}W6ph5Mm5Pz8GgiULbPgzG37mj9g=");
    TestResource resource = new TestResource();
    resource.setPath(passwordFilePath);

    UserAuthenticator authenticator = new UserPasswordAuthenticator(
        request, resource);

    assertTrue(authenticator.isAuthenticated());
  }

  @Test
  public void testIsNotAuthenticatedWithInvalidPassword() throws IOException {
    HttpRequest request = new HttpRequest();
    request.addHeader("Authorization: Basic anJvYjpub3RhcGFzc3dvcmQ=");

    Path passwordFilePath = createPasswordsFile("jrob:{SHA-1}W6ph5Mm5Pz8GgiULbPgzG37mj9g=");
    TestResource resource = new TestResource();
    resource.setPath(passwordFilePath);

    UserAuthenticator authenticator = new UserPasswordAuthenticator(
        request, resource);

    assertFalse(authenticator.isAuthenticated());
  }
}
