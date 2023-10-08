package tests.helpers.requests;

import java.io.IOException;
import java.io.InputStream;

public class TestInputStream extends InputStream {

  private int index = 0;
  private byte[] testContent;

  public TestInputStream(byte[] testContent) {
    this.testContent = testContent;
  }

  @Override
  public int read() throws IOException {
    if (index == testContent.length) {
      return -1;
    } else {
      return testContent[index++];
    }
  }

  @Override
  public int read(byte[] destination) {
    int result = destination.length;

    for (int offset = 0; offset < destination.length; offset++) {
      destination[offset] = testContent[this.index++];
    }

    return result;
  }
}
