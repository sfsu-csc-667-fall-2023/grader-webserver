package tests.webserver667.requests;

import java.io.IOException;

import org.junit.jupiter.api.Test;

// + RequestHandler(socket: Socket): RequestHandler

public class _RequestHandlerTest {
  // private static Path createDocumentRoot(Path path) throws IOException {
  // Path documentRoot = Files.createTempDirectory("documentRoot");

  // Files.createDirectories(Paths.get(
  // documentRoot.toString(),
  // path.toString()));

  // return documentRoot;
  // }

  @Test
  public void testSuccessfulRequest() throws IOException {
    // Otherwise 200
    // Socket socket = new Socket();

    // Path resourcePath = Paths.get("/");
    // Path documentRoot = createDocumentRoot(resourcePath);

    // RequestHandler handler = new RequestHandler(socket, documentRoot.toString());

    // Resource resource = new Resource("/index.html", documentRoot.toString());
    // Path filePath = Files.createTempFile(
    // Paths.get(documentRoot.toString(), resourcePath.toString(), "index.html"),
    // "index", "html");

    // FileWriter fileWriter = new FileWriter(filePath.toFile());
    // fileWriter.write("content");
    // fileWriter.close();

    // byte[] responseHead = "HTTP/1.1 200 Ok\r\nContent-Length:
    // 7\r\n\r\n".getBytes();
    // byte[] content = "content".getBytes();

    // // Create the expected response as a byte[]
    // ByteBuffer buffer = ByteBuffer.wrap(new byte[responseHead.length +
    // content.length]);
    // buffer.put(responseHead);
    // buffer.put(content);

    // OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
    // writer.write("GET /index.html HTTP/1.1\r\n\r\n");

    // InputStreamReader reader = new InputStreamReader(socket.getInputStream());

  }

  // Bad request results in a 400

  // Server failure results in a 500

  // Logging occurs
}
