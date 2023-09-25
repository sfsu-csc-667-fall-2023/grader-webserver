package tests.webserver667.requests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import tests.dataProviders.RequestReaderTestProviders;
import webserver667.exceptions.BadRequestException;
import webserver667.requests.HttpMethods;
import webserver667.requests.HttpRequest;
import webserver667.requests.RequestReader;

public class RequestReaderTest {

  @ParameterizedTest
  @MethodSource("tests.dataProviders.RequestReaderTestProviders#provideValidHttpMethods")
  public void testValidHttpMethods(HttpMethods expectedMethod, InputStream input) {
    RequestReader reader = new RequestReader(input);
    HttpRequest request = reader.getRequest();

    assertEquals(expectedMethod, request.getHttpMethod());
  }

  @ParameterizedTest
  @MethodSource("tests.dataProviders.RequestReaderTestProviders#provideInvalidHttpMethods")
  public void testInvalidHttpMethods(InputStream input) {
    RequestReader reader = new RequestReader(input);

    assertThrows(
        BadRequestException.class,
        () -> {
          reader.getRequest();
        });
  }

  @ParameterizedTest
  @MethodSource("tests.dataProviders.RequestReaderTestProviders#provideURIs")
  public void testRequestReaderReadsURI(String expectedUri, InputStream input) {
    RequestReader reader = new RequestReader(input);
    HttpRequest request = reader.getRequest();

    assertEquals(expectedUri, request.getURI());
  }

  @ParameterizedTest
  @MethodSource("tests.dataProviders.RequestReaderTestProviders#provideURIsWithQueryStrings")
  public void testRequestReaderReadsQueryStringsFromURIs(String expectedQueryString, InputStream input) {
    RequestReader reader = new RequestReader(input);
    HttpRequest request = reader.getRequest();

    assertEquals(expectedQueryString, request.getQueryString());
  }

  @Test
  public void testRequestReaderReadsHttpVersion() {
    RequestReader reader = new RequestReader(
        RequestReaderTestProviders.createTestStream("GET /index.html HTTP/1.1\r\n\r\n"));
    HttpRequest request = reader.getRequest();

    assertEquals("HTTP/1.1", request.getVersion());
  }

  @ParameterizedTest
  @MethodSource("tests.dataProviders.RequestReaderTestProviders#getHeaders")
  public void testRequestReaderReadsHeaders(String expectedHeaderName, String expectedHeaderValue, InputStream input) {
    RequestReader reader = new RequestReader(input);
    HttpRequest request = reader.getRequest();

    assertEquals(expectedHeaderValue, request.getHeader(expectedHeaderName));
  }

  @Test
  public void testRequestReaderReadsBody() {
    byte[] expectedBody = new byte[] { 42, 3, 5, 10, 22, 38 };
    byte[] initialRequestContent = "GET / HTTP/1.1\r\nContent-Length: 6\r\n\r\n".getBytes();

    byte[] requestContent = new byte[expectedBody.length + initialRequestContent.length];
    for (int index = 0; index < initialRequestContent.length; index++) {
      requestContent[index] = initialRequestContent[index];
    }
    for (int index = 0; index < expectedBody.length; index++) {
      requestContent[index + initialRequestContent.length] = expectedBody[index];
    }

    InputStream input = new InputStream() {
      private int index = 0;

      @Override
      public int read() {
        return requestContent[index++];
      }
    };

    RequestReader reader = new RequestReader(input);
    HttpRequest request = reader.getRequest();

    boolean areCorrectBytesPresent = true;
    byte[] requestObjectBytes = request.getBody();

    for (int index = 0; index < requestObjectBytes.length; index++) {
      areCorrectBytesPresent = areCorrectBytesPresent && requestObjectBytes[index] == expectedBody[index];
    }
    areCorrectBytesPresent = areCorrectBytesPresent && requestObjectBytes.length == expectedBody.length;

    assertTrue(areCorrectBytesPresent);
  }

  @Test
  public void testRequestReaderSetsNullForEmptyBody() {
    RequestReader reader = new RequestReader(
        RequestReaderTestProviders.createTestStream("GET /index.html HTTP/1.1\r\n\r\n"));
    HttpRequest request = reader.getRequest();

    assertEquals(0, request.getBody().length);
  }
}
