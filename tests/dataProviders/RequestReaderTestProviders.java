package tests.dataProviders;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import webserver667.requests.HttpMethods;

public class RequestReaderTestProviders {

  public static InputStream createTestStream(String testContent) {
    return new InputStream() {
      private int index = 0;

      @Override
      public int read() throws IOException {
        return testContent.charAt(index++);
      }

    };
  }

  public static Stream<Arguments> provideValidHttpMethods() {
    return Stream.of(
        Arguments.of(HttpMethods.GET, createTestStream("GET / HTTP/1.1\r\n\r\n")),
        Arguments.of(HttpMethods.HEAD, createTestStream("HEAD / HTTP/1.1\r\n\r\n")),
        Arguments.of(HttpMethods.POST, createTestStream("POST / HTTP/1.1\r\n\r\n")),
        Arguments.of(HttpMethods.PUT, createTestStream("PUT / HTTP/1.1\r\n\r\n")),
        Arguments.of(HttpMethods.DELETE, createTestStream("DELETE / HTTP/1.1\r\n\r\n")));
  }

  public static Stream<Arguments> provideInvalidHttpMethods() {
    return Stream.of(
        Arguments.of(createTestStream("TRACE / HTTP/1.1\r\n\r\n")),
        Arguments.of(createTestStream("BLARG / HTTP/1.1\r\n\r\n")));
  }

  public static Stream<Arguments> provideURIs() {
    return Stream.of(
        Arguments.of("/index.html", createTestStream("GET /index.html HTTP/1.1\r\n\r\n")),
        Arguments.of("/blarg/saxifrage/some-filename_etc.png",
            createTestStream("GET /blarg/saxifrage/some-filename_etc.png HTTP/1.1\r\n\r\n")));
  }

  public static Stream<Arguments> provideURIsWithQueryStrings() {
    return Stream.of(
        Arguments.of("a=b&c=d", createTestStream("GET /index.html?a=b&c=d HTTP/1.1\r\n\r\n")),
        Arguments.of("this=is&a=setof&key=values",
            createTestStream(
                "GET /blarg/saxifrage/some-filename_etc.png?this=is&a=setof&key=values HTTP/1.1\r\n\r\n")));
  }

  public static Stream<Arguments> getHeaders() {
    return Stream.of(
        Arguments.of(
            "Authorization",
            "Basic ashgkasrhgkaharhqeth",
            createTestStream("GET / HTTP/1.1\r\nAuthorization: Basic ashgkasrhgkaharhqeth\r\n\r\n")),
        Arguments.of(
            "Content-Type",
            "text/html",
            createTestStream("GET / HTTP/1.1\r\nContent-Type: text/html\r\n\r\n")),
        Arguments.of(
            "X-Doesnt-Exist",
            null,
            createTestStream("GET / HTTP/1.1\r\nContent-Type: text/html\r\n\r\n")));
  }
}
