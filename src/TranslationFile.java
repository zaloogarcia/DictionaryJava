import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io  .FileWriter;
import java.io.IOException;

public class TranslationFile {
  private String fileString;
  
  private String[] fileWords;
  
  public FileNotFoundException readException =
      new FileNotFoundException("Could not find file meant for reading.");

  public TranslationFile(String path) throws java.lang.Exception {
    String line;
    fileString = "";
    try {
      FileReader fr = new FileReader(path);
      BufferedReader br = new BufferedReader(fr);
      line = br.readLine();
      while (line != null) {
        fileString += line;
        if ((line = br.readLine()) != null) {
          fileString += "\n";
        }
      }
      br.close();
      fileString = fileString.replaceAll("[¿¡]", "");
      fileWords = fileString.split("[^\\p{Ll}\\p{Lu}]");
    } catch (FileNotFoundException ex) {
      throw readException;
    } catch (IOException ex) {
      throw new IOException("Error opening file.");
    }
  }

  public void unload(String path) {
    try {
      FileWriter fw = new FileWriter(path);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(fileString);
      bw.write("\n");
      bw.close();
    } catch (IOException ex) {
      System.out.println("Error making file.");
    }
  }

  public void translateWord(String word, String replacement, int ind) {
    int count = 0;
    for (int i = 0; i < ind - 1; i++) {
      if (fileWords[i].equals(word)) {
        count = fileString.indexOf(word, count);
        count += word.length();
      }
    }
    count = fileString.indexOf(word, count);
    fileString = fileString.substring(0, count)
      + replacement + fileString.substring(count + word.length());
    fileWords[ind - 1] = replacement;
  }

  public String wordNumber(int wordNumber) {
    return fileWords[wordNumber - 1];
  }

  public int numOfWords() {
    return fileWords.length;
  }

  public String getFile() {
    return fileString;
  }
}

