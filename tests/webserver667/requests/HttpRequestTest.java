package tests.webserver667.requests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import webserver667.requests.HttpMethods;
import webserver667.requests.HttpRequest;

public class HttpRequestTest {

  @ParameterizedTest
  @MethodSource("provideHttpMethods")
  public void testGetMethod(HttpMethods method) {
    HttpRequest request = new HttpRequest();
    request.setHttpMethod(method);

    assertEquals(method, request.getHttpMethod());
  }

  @ParameterizedTest
  @MethodSource("provideURIs")
  public void testGetURI(String uri, String expected) {
    HttpRequest request = new HttpRequest();
    request.setURI(uri);

    assertEquals(expected, request.getURI());
  }

  @ParameterizedTest
  @MethodSource("provideQueryStringURIs")
  public void testGetQueryString(String uri, String expected) {
    HttpRequest request = new HttpRequest();
    request.setURI(uri);

    assertEquals(expected, request.getQueryString());
  }

  @Test
  public void testGetVersion() {
    String expected = "HTTP/1.1";

    HttpRequest request = new HttpRequest();
    request.setVersion(expected);

    assertEquals(expected, request.getVersion());
  }

  @ParameterizedTest
  @MethodSource("provideHttpHeaderLines")
  public void testGetHeader(String headerLine, String expectedHeaderKey, String expectedHeaderValue) {
    HttpRequest request = new HttpRequest();
    request.addHeader(headerLine);

    assertEquals(expectedHeaderValue, request.getHeader(expectedHeaderKey));
  }

  @Test
  public void testGetContentLength() {
    int contentLength = 42;

    HttpRequest request = new HttpRequest();
    request.addHeader(String.format("Content-Length: %d", contentLength));

    assertEquals(contentLength, request.getContentLength());
  }

  @Test
  public void testGetContentLengthWhenNoContentLengthHeader() {
    HttpRequest request = new HttpRequest();

    assertEquals(0, request.getContentLength());
  }

  @Test
  public void testHasBody() {
    int size = 142;
    byte[] bytes = new byte[size];

    HttpRequest request = new HttpRequest();
    request.addHeader("Content-Length: 142");
    request.setBody(bytes);

    assertTrue(request.hasBody());
  }

  @Test
  public void testDoesntHasBody() {
    HttpRequest request = new HttpRequest();

    assertFalse(request.hasBody());
  }

  @Test
  public void testGetBody() {
    int size = 142;
    byte[] bytes = new byte[size];

    HttpRequest request = new HttpRequest();
    request.setBody(bytes);

    assertEquals(bytes, request.getBody());
  }

  @Test
  public void testGetBodyWhenNoBody() {
    HttpRequest request = new HttpRequest();

    byte[] result = request.getBody();
    assertEquals(0, result.length, "HttpRequest#getBody() should return an empty byte[] when no body is present.");
  }

  private static Stream<Arguments> provideHttpMethods() {
    return Stream.of(
        Arguments.of(HttpMethods.GET),
        Arguments.of(HttpMethods.HEAD),
        Arguments.of(HttpMethods.POST),
        Arguments.of(HttpMethods.PUT),
        Arguments.of(HttpMethods.DELETE));
  }

  private static Stream<Arguments> provideURIs() {
    return Stream.of(
        Arguments.of("/index.html", "/index.html"),
        Arguments.of("/index.html?this=is&a=querystring", "/index.html"));
  }

  private static Stream<Arguments> provideQueryStringURIs() {
    return Stream.of(
        Arguments.of("/index.html", null),
        Arguments.of("/index.html?this=is&a=querystring", "this=is&a=querystring"));
  }

  private static Stream<Arguments> provideHttpHeaderLines() {
    return Stream.of(
        Arguments.of("Content-Type: text/html\r\n", "Content-Type", "text/html"),
        Arguments.of("X-Header-Made-Up: this is the header value\r\n", "X-Header-Made-Up", "this is the header value"));
  }
}
