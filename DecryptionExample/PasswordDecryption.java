package DecryptionExample;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class PasswordDecryption {
  // Header from client: Authorization: Basic anJvYjpwYXNzd29yZA==
  // Base64 encoded; decode that => jrob:password
  // take the password portion and SHA encode

  protected static Credentials decodeBase64EncodedCredentials(
      String authorizationHeaderValue) throws NoSuchAlgorithmException {

    // authInfo is provided in the header received from the client
    // as a Base64 encoded string.
    String credentials = new String(
        Base64.getDecoder().decode(authorizationHeaderValue.replace("Basic ", "")),
        Charset.forName("UTF-8"));

    // The string is the key:value pair username:password
    String[] tokens = credentials.split(":");

    return new Credentials(tokens[0].trim(), tokens[1].trim());
  }

  public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
    Credentials credentials = decodeBase64EncodedCredentials("Basic anJvYjpwYXNzd29yZA==");

    Map<String, String> passwordFileEntries = new HashMap<>();

    Files.readAllLines(Paths.get("sample_files", ".htpasswd")).stream().forEach(line -> {
      // jrob:{SHA}W6ph5Mm5Pz8GgiULbPgzG37mj9g=
      String[] lineParts = line.replace("{SHA}", "").split(":");

      passwordFileEntries.put(lineParts[0].trim(), lineParts[1].trim());
    });

    String passwordFilePassword = passwordFileEntries.get(credentials.username);

    System.out.println(
        String.format(
            "User %s %s authorized.",
            credentials.username,
            passwordFileEntries != null && passwordFilePassword.equals(credentials.shaEncodedPassword) ? "is"
                : "is not"));

    passwordFilePassword = passwordFileEntries.get("someone else");
    System.out.println(
        String.format(
            "User %s %s authorized.",
            "someone else",
            passwordFilePassword != null && passwordFilePassword.equals(credentials.shaEncodedPassword) ? "is"
                : "is not"));
  }
}
