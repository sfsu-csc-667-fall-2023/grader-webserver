package tests.helpers.responses;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class TestOutputStream extends OutputStream {

  private ByteArrayOutputStream out;
  private int streamIndex;
  private int bodyPointer;
  private StringBuffer buffer;

  public TestOutputStream() {
    this.out = new ByteArrayOutputStream();
    this.streamIndex = 0;
    this.bodyPointer = 0;
    this.buffer = new StringBuffer();
  }

  @Override
  public void write(int b) throws IOException {
    this.out.write(b);
    this.streamIndex++;

    char charValue = (char) b;
    if (charValue == '\r' || charValue == '\n') {
      buffer.append(charValue);
    } else {
      buffer.delete(0, buffer.length() - 1);
    }

    if (buffer.length() == 4) {
      this.bodyPointer = this.streamIndex;
    }
  }

  @Override
  public String toString() {
    byte[] result = out.toByteArray();

    return new String(
        Arrays.copyOfRange(result, 0, bodyPointer - 1));
  }

  public byte[] getBody() {
    if (this.bodyPointer == 0) {
      return new byte[0];
    } else {
      byte[] result = out.toByteArray();

      return Arrays.copyOfRange(result, bodyPointer, result.length - 1);
    }
  }
}
