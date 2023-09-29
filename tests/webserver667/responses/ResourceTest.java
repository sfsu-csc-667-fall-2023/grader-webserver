package tests.webserver667.responses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import webserver667.responses.Resource;

public class ResourceTest {
  private static Path createDocumentRoot(Path path) throws IOException {
    Path documentRoot = Files.createTempDirectory("documentRoot");

    Files.createDirectories(Paths.get(
        documentRoot.toString(),
        path.toString()));

    return documentRoot;
  }

  @Test
  public void testExistsForExistingFile() throws IOException {
    Path resourcePath = Paths.get("doesnt", "matter");
    Path documentRoot = createDocumentRoot(resourcePath);

    Resource resource = new Resource("/doesnt/matter/index.html", documentRoot.toString());
    Files.createTempFile(
        Paths.get(documentRoot.toString(), resourcePath.toString()), "index", "html");

    assertTrue(resource.exists());
  }

  @Test
  public void testExistsForNoFile() throws IOException {
    Path resourcePath = Paths.get("doesnt", "matter");
    Path documentRoot = createDocumentRoot(resourcePath);

    Resource resource = new Resource("/doesnt/matter/index.html", documentRoot.toString());

    assertFalse(resource.exists());
  }

  @Test
  public void testGetPath() throws IOException {
    Path resourcePath = Paths.get("doesnt", "matter", "index.html");
    Path documentRoot = createDocumentRoot(resourcePath);

    Resource resource = new Resource("/doesnt/matter/index.html", documentRoot.toString());
    Path expectedPath = Paths.get(documentRoot.toString(), resourcePath.toString());

    assertEquals(expectedPath, resource.getPath());
  }

  @Test
  public void testIsProtectedWhenDirectoryContainsPasswordsFile() throws IOException {
    Path resourcePath = Paths.get("doesnt", "matter");
    Path documentRoot = createDocumentRoot(resourcePath);

    Resource resource = new Resource("/doesnt/matter/index.html", documentRoot.toString());
    Files.createTempFile(
        Paths.get(documentRoot.toString(), resourcePath.toString()), null, "passwords");

    assertTrue(resource.isProtected());
  }

  @Test
  public void testIsProtectedWhenDirectoryDoesNotContainsPasswordsFile() throws IOException {
    Path resourcePath = Paths.get("doesnt", "matter");
    Path documentRoot = createDocumentRoot(resourcePath);

    Resource resource = new Resource("/doesnt/matter/index.html", documentRoot.toString());

    assertFalse(resource.isProtected());
  }

  @Test
  public void testIsScriptWhenInScriptDirectory() throws IOException {
    Path resourcePath = Paths.get("doesnt", "scripts");
    Path documentRoot = createDocumentRoot(resourcePath);

    Resource resource = new Resource("/doesnt/scripts/index.html", documentRoot.toString());

    assertTrue(resource.isScript());
  }

  @Test
  public void testIsScriptWhenNotInScriptDirectory() throws IOException {
    Path resourcePath = Paths.get("doesnt", "matter");
    Path documentRoot = createDocumentRoot(resourcePath);

    Resource resource = new Resource("/doesnt/matter/index.html", documentRoot.toString());

    assertTrue(resource.isScript());
  }
}
