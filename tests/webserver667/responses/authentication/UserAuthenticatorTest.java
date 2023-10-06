package tests.webserver667.responses.authentication;

import org.junit.jupiter.api.Test;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.authentication.UserAuthenticator;
import webserver667.responses.authentication.UserPasswordAuthenticator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static tests.dataProviders.ResponseWriterTestProviders.*;

public class UserAuthenticatorTest {
  @Test
  public void testIsAuthenticatedWithValidPassword() {
    HttpRequest request = new HttpRequest();
    request.addHeader("Authorization: Basic anJvYjpwYXNzd29yZA==");

    IResource resource = createTestResource(
        "jrob:{SHA}W6ph5Mm5Pz8GgiULbPgzG37mj9g=", "", ".passwords", "");

    UserAuthenticator authenticator = new UserPasswordAuthenticator(
        request, resource);

    assertTrue(authenticator.isAuthenticated());
  }

  @Test
  public void testIsAuthenticatedWithInvalidPassword() {
    HttpRequest request = new HttpRequest();
    request.addHeader("Authorization: Basic anJvYjpub3RhcGFzc3dvcmQ=");

    IResource resource = createTestResource(
        "jrob:{SHA}W6ph5Mm5Pz8GgiULbPgzG37mj9g=", "", ".passwords", "");

    UserAuthenticator authenticator = new UserPasswordAuthenticator(
        request, resource);

    assertFalse(authenticator.isAuthenticated());
  }
}
