package tests.helpers.responses;

public class CompareTestOutputAndExpectedOutput {
  private byte[] responseHead;
  private byte[] responseBody;

  public CompareTestOutputAndExpectedOutput(TestOutputStream output) {

    this.responseHead = output.getResponseHead();
    this.responseBody = output.getBody();
  }

  public boolean headContains(byte[] header) {
    return search(responseHead, header);
  }

  public boolean bodyContains(byte[] expectedBody) {
    return search(responseBody, expectedBody);
  }

  private boolean search(byte[] haystack, byte[] needle) {
    for (int i = 0; i <= haystack.length - needle.length; i++) {
      if (match(haystack, needle, i)) {
        return true;
      }
    }

    return false;
  }

  private boolean match(byte[] haystack, byte[] needle, int start) {
    if (needle.length + start > haystack.length) {
      return false;
    } else {
      for (int i = 0; i < needle.length; i++) {
        if (needle[i] != haystack[i + start]) {
          return false;
        }
      }

      return true;
    }
  }
}
