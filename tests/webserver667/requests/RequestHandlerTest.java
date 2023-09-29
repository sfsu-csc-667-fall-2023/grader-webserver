package tests.webserver667.requests;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import webserver667.requests.RequestHandler;
import webserver667.responses.Resource;

// + RequestHandler(socket: Socket): RequestHandler

public class RequestHandlerTest {
  private static Path createDocumentRoot(Path path) throws IOException {
    Path documentRoot = Files.createTempDirectory("documentRoot");

    Files.createDirectories(Paths.get(
        documentRoot.toString(),
        path.toString()));

    return documentRoot;
  }

  @Test
  public void testSuccessfulRequest() throws IOException {
    // Otherwise 200
    Socket socket = new Socket();
    RequestHandler handler = new RequestHandler(socket);

    Path resourcePath = Paths.get("/");
    Path documentRoot = createDocumentRoot(resourcePath);

    Resource resource = new Resource("/index.html", documentRoot.toString());
    Path filePath = Files.createTempFile(
        Paths.get(documentRoot.toString(), resourcePath.toString(), "index.html"), "index", "html");

    FileWriter fileWriter = new FileWriter(filePath.toFile());
    fileWriter.write("content");
    fileWriter.close();

    byte[] responseHead = "HTTP/1.1 200 Ok\r\nContent-Length: 7\r\n\r\n".getBytes();
    byte[] content = "content".getBytes();

    // Create the expected response as a byte[]
    ByteBuffer buffer = ByteBuffer.wrap(new byte[responseHead.length + content.length]);
    buffer.put(responseHead);
    buffer.put(content);

    OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
    writer.write("GET /index.html HTTP/1.1\r\n\r\n");

    InputStreamReader reader = new InputStreamReader(socket.getInputStream());

  }

  // Bad request results in a 400

  // Server failure results in a 500

  // Logging occurs
}
