package DecryptionExample;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Credentials {
  public String username;
  public String password;
  public String shaEncodedPassword;

  public Credentials(String username, String password) throws NoSuchAlgorithmException {
    this.username = username;
    this.password = password;

    MessageDigest mDigest = MessageDigest.getInstance("SHA-1");
    byte[] result = mDigest.digest(this.password.getBytes());

    this.shaEncodedPassword = Base64.getEncoder().encodeToString(result);
  }
}
