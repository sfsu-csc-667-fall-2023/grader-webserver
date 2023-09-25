package tests.webserver667.responses;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import webserver667.responses.HttpResponse;
import webserver667.responses.HttpResponseCode;

public class HttpResponseTest {

  @Test
  public void testStatus() {
    HttpResponse response = new HttpResponse();
    response.setStatus(HttpResponseCode.BAD_REQUEST);

    assertEquals(HttpResponseCode.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void testVersion() {
    String expectedVersion = "HTTP/1.0";

    HttpResponse response = new HttpResponse();
    response.setVersion(expectedVersion);

    assertEquals(expectedVersion, response.getVersion());
  }

  @Test
  public void testHeaders() {
    String expectedHeaderKey = "Content-Length";
    String expectedHeaderValue = "142";

    HttpResponse response = new HttpResponse();
    response.addHeader("Content-Length: 142");

    assertEquals(expectedHeaderValue, response.getHeader(expectedHeaderKey));
  }
}
