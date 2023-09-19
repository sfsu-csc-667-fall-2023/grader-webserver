package tests.webserver667.requests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import webserver667.requests.HttpRequest;
import webserver667.requests.HttpMethods;
import webserver667.requests.RequestReader;

public class RequestReaderTest {

  private InputStream createTestStream(String testContent) {
    return new InputStream() {
      private int index = 0;

      @Override
      public int read() throws IOException {
        return testContent.charAt(index++);
      }

    };
  }

  @ParameterizedTest
  @MethodSource("provideValidHttpMethods")
  public void testValidHttpMethods(HttpMethods expectedMethod, InputStream input) {
    RequestReader reader = new RequestReader(input);
    HttpRequest request = reader.getRequest();

    assertEquals(expectedMethod, request.getHttpMethod());
  }

  public void testInvalidHttpMethods() {
    RequestReader reader = new RequestReader(createTestStream("TRACE / HTTP/1.1\r\n\r\n"));
    HttpRequest request = reader.getRequest();

    // Test that an exception is thrown (write an exception)
  }

  public Stream<Arguments> provideValidHttpMethods() {
    return Stream.of(
        Arguments.of(HttpMethods.GET, createTestStream("GET / HTTP/1.1\r\n\r\n")),
        Arguments.of(HttpMethods.HEAD, createTestStream("HEAD / HTTP/1.1\r\n\r\n")),
        Arguments.of(HttpMethods.POST, createTestStream("POST / HTTP/1.1\r\n\r\n")),
        Arguments.of(HttpMethods.PUT, createTestStream("PUT / HTTP/1.1\r\n\r\n")),
        Arguments.of(HttpMethods.DELETE, createTestStream("DELETE / HTTP/1.1\r\n\r\n")));
  }
}
