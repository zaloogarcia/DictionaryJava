import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Translator {
    
  public static void main(String[] args) { 

    Dictionary dict = new Dictionary();
    IgnoredSet ignored = new IgnoredSet();
    Cli arguments = new Cli(args);
    TranslationFile memfile;

    arguments.parse(); 
    if (arguments.choices[0] != null) {
      try {
        memfile = new TranslationFile(arguments.choices[0]);
      } catch (Exception ex) {
        System.out.println("Input file:");
        printError(ex);
        return;
      }
    } else {
      System.out.println("Debe especificar un archivo de entrada.");
      return;
    }       
    if (arguments.choices[1] != null) {
      try {
        dict.load(arguments.choices[1], arguments.reverse);
      } catch (Exception ex) {
        System.out.println("Dictionary file:");
        printError(ex);
        return;
      }
    }
    if (arguments.choices[2] != null) {
      try {
        ignored.load(arguments.choices[2]);
      } catch (Exception ex) {
        System.out.println("Ignored file:");
        printError(ex);
        return;
      }
    }
    translate(memfile, dict, ignored);
    if (!dict.isEmpty()) {
      if (arguments.choices[1] != null) {
        try {
          dict.unload(arguments.choices[1], arguments.reverse);
        } catch (Exception ex) {
          printError(ex);
          return;
        } 
      } else {
        dict.unload("dictionary.txt", arguments.reverse);
      }
    }
    if (!ignored.isEmpty()) {
      if (arguments.choices[2] != null) {
        try {
          ignored.unload(arguments.choices[2]);
        } catch (Exception ex) {
          printError(ex);
          return;
        }
      } else {
        ignored.unload("ignored_words.txt");
      }
    }
    if (arguments.choices[3] != null) {
      memfile.unload(arguments.choices[3]);
    } else {
      memfile.unload("translation_output.txt");
    }
  }

  public static String altmenu(String word) {
          
    Scanner scan = new Scanner(System.in);
    String answer = "";    
                    
    System.out.print("\t\t No hay traducción para la palabra: " + word
                     + "\n\t\t Ignorar (i) - Ignorar Todas (h)\n\t\t "
                     + "Traducir como (t) - Traducir siempre como (s)\n");
    return scan.next();          
  }

  public static void translate(TranslationFile memfile, Dictionary dict,
      IgnoredSet ignored) {
    String translationWord = "";
    String translatedWord = "";
    String userChoice = "";
    Scanner scan = new Scanner(System.in);

    for (int i = 1; i <= memfile.numOfWords(); i++) {
      translationWord = memfile.wordNumber(i);
      if (!ignored.contains(translationWord)) {
        if (dict.containsKey(translationWord)) {
          translatedWord = dict.get(translationWord);
          memfile.translateWord(translationWord, translatedWord, i);
        } else {
          List<String> validInput = Arrays.asList("h", "s", "t", "i");
          boolean isValid = false;
          while (!isValid) {
            userChoice = altmenu(translationWord);
            isValid = validInput.contains(userChoice);
            if (!isValid) {
              System.out.println("Opcion no valida!");
            }
          }
          if (userChoice.equals("h")) {
            ignored.add(translationWord);
          } else if (userChoice.equals("s")) {
            System.out.println("Introducir traduccion:");
            translatedWord = scan.next();
            memfile.translateWord(translationWord, translatedWord, i);
            dict.put(translationWord, translatedWord);
          } else if (userChoice.equals("t")) {
            System.out.println("Introducir traduccion:");
            translatedWord = scan.next();
            memfile.translateWord(translationWord, translatedWord, i);
          }
        }
      }
    }
  }

  public static void printError(Exception ex) {
    String errorMessage = ex.getMessage();
    System.out.println(errorMessage);
  }

}
