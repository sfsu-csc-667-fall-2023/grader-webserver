package tests.helpers.requests;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import webserver667.requests.HttpMethods;

public class TestProviders {
  public static Stream<Arguments> provideValidHttpMethods() {
    return Stream.of(
        Arguments.of(HttpMethods.GET, new TestInputStream("GET / HTTP/1.1\r\n\r\n".getBytes())),
        Arguments.of(HttpMethods.HEAD, new TestInputStream("HEAD / HTTP/1.1\r\n\r\n".getBytes())),
        Arguments.of(HttpMethods.POST, new TestInputStream("POST / HTTP/1.1\r\n\r\n".getBytes())),
        Arguments.of(HttpMethods.PUT, new TestInputStream("PUT / HTTP/1.1\r\n\r\n".getBytes())),
        Arguments.of(HttpMethods.DELETE, new TestInputStream("DELETE / HTTP/1.1\r\n\r\n".getBytes())));
  }

  public static Stream<Arguments> provideInvalidHttpMethods() {
    return Stream.of(
        Arguments.of(new TestInputStream("TRACE / HTTP/1.1\r\n\r\n".getBytes())),
        Arguments.of(new TestInputStream("BLARG / HTTP/1.1\r\n\r\n".getBytes())));
  }

  public static Stream<Arguments> provideURIs() {
    return Stream.of(
        Arguments.of("/index.html", new TestInputStream("GET /index.html HTTP/1.1\r\n\r\n".getBytes())),
        Arguments.of("/blarg/saxifrage/some-filename_etc.png",
            new TestInputStream("GET /blarg/saxifrage/some-filename_etc.png HTTP/1.1\r\n\r\n".getBytes())));
  }

  public static Stream<Arguments> provideURIsWithQueryStrings() {
    return Stream.of(
        Arguments.of("a=b&c=d", new TestInputStream("GET /index.html?a=b&c=d HTTP/1.1\r\n\r\n".getBytes())),
        Arguments.of("this=is&a=setof&key=values",
            new TestInputStream(
                "GET /blarg/saxifrage/some-filename_etc.png?this=is&a=setof&key=values HTTP/1.1\r\n\r\n".getBytes())));
  }

  public static Stream<Arguments> getHeaders() {
    return Stream.of(
        Arguments.of(
            "Authorization",
            "Basic ashgkasrhgkaharhqeth",
            new TestInputStream("GET / HTTP/1.1\r\nAuthorization: Basic ashgkasrhgkaharhqeth\r\n\r\n".getBytes())),
        Arguments.of(
            "Content-Type",
            "text/html",
            new TestInputStream("GET / HTTP/1.1\r\nContent-Type: text/html\r\n\r\n".getBytes())),
        Arguments.of(
            "X-Doesnt-Exist",
            null,
            new TestInputStream("GET / HTTP/1.1\r\nContent-Type: text/html\r\n\r\n".getBytes())));
  }
}
