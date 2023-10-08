package tests.helpers.responses;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class TestOutputStream extends OutputStream {

  private ByteArrayOutputStream out;
  private int streamIndex;
  private int bodyPointer;
  private int crlfCounter;

  public TestOutputStream() {
    this.out = new ByteArrayOutputStream();
    this.streamIndex = 0;
    this.bodyPointer = 0;
    this.crlfCounter = 0;
  }

  @Override
  public void write(int b) throws IOException {
    this.out.write(b);
    this.streamIndex++;

    char charValue = (char) b;
    if (charValue == '\r' || charValue == '\n') {
      this.crlfCounter++;
    } else {
      this.crlfCounter = 0;
    }

    if (this.crlfCounter == 4 && this.crlfCounter != -1) {
      this.bodyPointer = this.streamIndex;
      this.crlfCounter = -1;
    }
  }

  @Override
  public void write(byte[] b) throws IOException {
    for (int i = 0; i < b.length; i++) {
      this.write(b[i]);
    }
  }

  public byte[] getResponseHead() {
    byte[] result = out.toByteArray();

    if (this.bodyPointer == 0) {
      return result;
    } else {
      return Arrays.copyOfRange(result, 0, bodyPointer);
    }
  }

  public byte[] getBody() {
    if (this.bodyPointer == 0) {
      return new byte[0];
    } else {
      byte[] result = out.toByteArray();

      return Arrays.copyOfRange(result, bodyPointer, result.length);
    }
  }
}
