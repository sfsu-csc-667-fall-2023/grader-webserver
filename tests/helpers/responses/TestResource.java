package tests.helpers.responses;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.authentication.UserAuthenticator;

public class TestResource implements IResource {

  public static Path createTempResourceFile(String fileName, String fileExtension, String fileContent)
      throws IOException {
    Path path = Files.createTempFile(fileName, fileExtension);

    FileWriter writer = new FileWriter(path.toFile());
    writer.write(fileContent);
    writer.flush();
    writer.close();

    return path;
  }

  private boolean existsTestValue;
  private boolean isProtectedTestValue;
  private boolean isScriptTestValue;
  private String mimeTypeTestValue;
  private UserAuthenticator userAuthenticatorTestValue;
  private Path pathTestValue;
  private boolean shouldReportModifiedTestValue;

  /**
   * Creates a concrete implementation of IResource that describes a file:
   * - that exists
   * - that is not protected
   * - that is not a script
   * - that has a valid path
   * - that has a mime type of text/html
   *
   * @throws IOException
   */
  public TestResource() throws IOException {
    this.existsTestValue = true;
    this.isProtectedTestValue = false;
    this.isScriptTestValue = false;
    this.pathTestValue = createTempResourceFile("index-test", ".html", Contents.HTML_FILE);
    this.mimeTypeTestValue = "text/html";
    this.userAuthenticatorTestValue = new TestUserAuthenticator();
  }

  public void setExists(boolean existsTestValue) {
    this.existsTestValue = existsTestValue;
  }

  public void setIsProtected(boolean isProtectedTestValue) {
    this.isProtectedTestValue = isProtectedTestValue;
  }

  public void setIsScript(boolean isScriptTestValue) {
    this.isScriptTestValue = isScriptTestValue;
  }

  public void setMimeType(String mimeType) {
    this.mimeTypeTestValue = mimeType;
  }

  public void setUserAuthenticator(UserAuthenticator testUserAuthenticator) {
    this.userAuthenticatorTestValue = testUserAuthenticator;
  }

  public void setPath(Path path) {
    this.pathTestValue = path;
  }

  public long getFileSize() throws IOException {
    return Files.size(this.getPath());
  }

  public void shouldReportModified(boolean answer) {
    this.shouldReportModifiedTestValue = answer;
  }

  @Override
  public boolean exists() {
    return this.existsTestValue;
  }

  @Override
  public Path getPath() {
    return this.pathTestValue;
  }

  public String getFileName() {
    return this.pathTestValue.getFileName().toString();
  }

  @Override
  public boolean isProtected() {
    return this.isProtectedTestValue;
  }

  @Override
  public boolean isScript() {
    return this.isScriptTestValue;
  }

  @Override
  public UserAuthenticator getUserAuthenticator(HttpRequest request) {
    return this.userAuthenticatorTestValue;
  }

  @Override
  public String getMimeType() {
    return this.mimeTypeTestValue;
  }

  @Override
  public byte[] getFileBytes() throws IOException {
    return Files.readAllBytes(pathTestValue);
  }

  @Override
  public long lastModified() {
    if (this.shouldReportModifiedTestValue) {
      return this.pathTestValue.toFile().lastModified();
    } else {
      return 0;
    }
  }
}
