import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class IgnoredSet extends HashSet<String> {

  public FileNotFoundException readException =
      new FileNotFoundException("Could not find file meant for reading.");
  
  public IgnoredSet() {
    super();
  }

  public void load(String path) throws java.lang.Exception {
    // Load ignore words set.
    String line;
    try {
      FileReader fr = new FileReader(path);
      BufferedReader br = new BufferedReader(fr);
      while ((line = br.readLine()) != null) {
        add(line);
      }
      br.close();
    } catch (FileNotFoundException ex) {
      throw readException;
    } catch (IOException ex) {
      throw new IOException("Could not open file.");
    }
  }
    
  public void unload(String path) {
    // Unload ignore words set.
    try {
      FileWriter fw = new FileWriter(path);
      BufferedWriter bw = new BufferedWriter(fw);
      for (String entry : this) {
        bw.write(entry + "\n");
      }
      bw.close();
    } catch (IOException ex) {
      System.out.println("Error writing to file '" + path + "'");
    }
  }       
}

