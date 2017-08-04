import java.util.*;
import java.util.logging.*;
import org.apache.commons.cli.*; //CLI version 1.3 

public class Cli {

  public boolean reverse;
  
  public String[] choices = {null, null, null, null};
  //Array for options values.
  private static final Logger log = Logger.getLogger(Cli.class.getName());
  //Logger for define de hierarchy of the printed options.
  private String[] args = null;   
  private Options options = new Options();

  public Cli(String[] args) {
    // Printing Interface.
    this.args = args;
    reverse = false;
    options.addOption("i", "input=FILE", true, "Documento de entrada." 
                      + "(Requerido)");
    options.addOption("d", "dictionary=FILE", true, "Diccionario de "
                      + "traduccion.");
    options.addOption("g", "ignored=FILE", true, "Diccionario de palabras"
                      + "ignoradas.");
    options.addOption("o", "output=FILE", true, "Archivo de salida.");
    options.addOption("r", "reverse", false, "Direccion de la traduccion");
  }

  public void parse() {
    // Parsing arguments.
    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = null;
    try {
      cmd = parser.parse(options, args);
      //Saving the arguments un choices array.   
      if (cmd.hasOption("i")) {
        choices[0] = cmd.getOptionValue("i");
      }
      if (cmd.hasOption("d")) {
        choices[1] = cmd.getOptionValue("d");
      }
      if (cmd.hasOption("g")) {
        choices[2] = cmd.getOptionValue("g");
      }
      if (cmd.hasOption("o")) {
        choices[3] = cmd.getOptionValue("o");
      }
      if (cmd.hasOption("r")) {
        reverse = true;
      }
    } catch (ParseException e) {
      log.log(Level.SEVERE, "Failed to parse comand line properties", e);
      help();
    }
  }

  private void help() {
    // This prints out some help
    HelpFormatter formater = new HelpFormatter();
    formater.printHelp("Main", options);
    System.exit(0);
  }
}
