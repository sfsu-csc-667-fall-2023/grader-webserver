package ProcessBuilderExample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;

public class ScriptRunner {
  public static void main(String[] args) throws IOException {
    ProcessBuilder builder = new ProcessBuilder("ProcessBuilderExample/shexample.sh");

    Map<String, String> env = builder.environment();
    env.put("HTTP_METHOD", "GET");
    env.put("HTTP_CONTENT-LENGTH", "100");

    Process scriptProcess = builder.start();

    OutputStreamWriter writer = new OutputStreamWriter(
        scriptProcess.getOutputStream());
    writer.write("This is the input from outside the process");

    BufferedReader reader = new BufferedReader(
        new InputStreamReader(scriptProcess.getInputStream()));

    System.out.println("----- Script output was -----");
    String line = "";
    while ((line = reader.readLine()) != null) {
      System.out.println(line);
    }

  }
}
