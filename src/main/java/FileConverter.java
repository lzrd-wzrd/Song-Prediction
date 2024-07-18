import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Converts files to lists that can be used in the program
 * @author Ian Osborn
 * @version 1.0
 */
public class FileConverter {
    
    private static String errorMessage;
    public ArrayList<String> songTitles = new ArrayList<>();
    public ArrayList<String> songRatings = new ArrayList<>();

    /**
     * Converts file to usable list
     * @param argument
     * @param songValues
     * @throws FileNotFoundException
     */
    public void convertFileToList(String argument, ArrayList<String> songValues) throws FileNotFoundException {
        File file = new File(argument);
        try {
          Scanner scanner = new Scanner(new FileInputStream(file));

          while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            try {
              songValues.add(line);
            } catch (NumberFormatException e){
              errorMessage = "Error: File must only contain numbers and whitespaces";
              System.err.println(errorMessage);
              break;
            }
          }
        
          scanner.close();
        } catch (FileNotFoundException e) {
          errorMessage = "Error: File <" + file + "> not found.";
          System.err.println(errorMessage);
          throw e;
        }
    }
}
