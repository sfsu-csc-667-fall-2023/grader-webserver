package tests.helpers.responses;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.authentication.UserAuthenticator;

/**
 * Creates a concrete implementation of the UserAuthenticator abstract
 * base class for use in testing.
 */
public class TestUserAuthenticator extends UserAuthenticator {

  private boolean testIsAuthenticatedValue;

  /**
   * Creates a concrete implementation of the UserAuthenticator abstract
   * base class that always returns true for isAuthenticated.
   */
  public TestUserAuthenticator() {
    this(null, null, true);
  }

  /**
   * @param testIsAuthenticatedValue The test value that will be returned when
   *                                 isAuthenticated is called
   */
  public TestUserAuthenticator(boolean testIsAuthenticatedValue) {
    this(null, null, testIsAuthenticatedValue);
  }

  /**
   * Creates a concrete implementation of the UserAuthenticator abstract
   * base class that always returns true for isAuthenticated.
   *
   * @param request
   * @param resource
   */
  public TestUserAuthenticator(HttpRequest request, IResource resource) {
    this(request, resource, true);
  }

  public TestUserAuthenticator(HttpRequest request, IResource resource, boolean testIsAuthenticatedValue) {
    super(request, resource);
    this.testIsAuthenticatedValue = testIsAuthenticatedValue;
  }

  @Override
  public boolean isAuthenticated() {
    return this.testIsAuthenticatedValue;
  }
}
