import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Dictionary extends HashMap<String, String> {

  public FileNotFoundException readException =
      new FileNotFoundException("Could not find file meant for reading.");

  public Dictionary() {
    super();
  }

  public void load(String path, boolean reverse) throws java.lang.Exception {
    // Load Dictionary file.
    String line;
    String[] words;
    try {
      FileReader fr = new FileReader(path);
      BufferedReader br = new BufferedReader(fr);
      while ((line = br.readLine()) != null) {
        words = line.split(",");
        if (reverse) {
          put(words[1], words[0]);
        } else {
          put(words[0], words[1]);
        }        
      }
      br.close();
    } catch (FileNotFoundException ex) {
      throw readException;
    } catch (IOException ex) {
      throw new IOException("Could not open file.");
    }
  }

  public void unload(String path, boolean reverse) { // Unload Dictionary file.
    try {
      FileWriter fw = new FileWriter(path);
      BufferedWriter bw = new BufferedWriter(fw);
      for (Map.Entry<String, String> entry : entrySet()) {
        if (!reverse) {
          bw.write(entry.getKey() + "," + entry.getValue() + "\n");
        } else {
          bw.write(entry.getValue() + "," + entry.getKey() + "\n");
        }
      }
      bw.close();
    } catch (IOException ex) {
      System.out.println("Error writing to file '" + path + "'");
    }
  }
}

