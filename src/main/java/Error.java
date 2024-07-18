import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Deals with various user errors that may occur
 * @author Ian Osborn
 * @version 1.0
 */
public class Error {
    
    public String message = " ";

    /**
     * Checks if files sizes are different
     * @param songTitles
     * @param songRatings
     * @return
     */
    public boolean fileSizesDiffer(ArrayList<String> songTitles, ArrayList<String> songRatings) {
        if (songTitles.size() != songRatings.size())
            message = "Error: Number of songs in song file not matching that of ratings file.";
        return songTitles.size() != songRatings.size();
    }
    /**
     * Handles various errors that may occur from user input files
     * @param songs
     * @param ratings
     * @param outputFile
     * @return error message
     * @throws Exception
     */
    public String handleInputFiles(ArrayList<String> songs, ArrayList<String> ratings, String outputFile) throws Exception {
        if (songs.size() > 0 & ratings.size() == 0) {
            String[] songInfo = new String[songs.size()];
            for (int i = 0; i < songInfo.length; i++) {
                String songConcat = songs.get(i) + " UNDEFINED" + " UNDEFINED";
                songInfo[i] = songConcat;
            }
            try {
                FileWriter fileWriter = new FileWriter(outputFile);
                for (String str : songInfo) {
                    fileWriter.write(str + "\n");
                }
                fileWriter.close();
            } catch(IOException e) {

            }
            return "Ratings File Blank";
        }
        if (songs.size() == 0 & ratings.size() > 0) {
            throw new Exception("Error: Songs file is blank.");
        }
        if (songs.size() == 0 & ratings.size() == 0) {
            message = "Error: Both input files are blank.";
            return message;
        }
        for (String line : ratings) {
        String[] splitRatings = line.split(" ");
        for (String entry : splitRatings) {
            if (!isDouble(entry)) {
                return "Error: Invalid entry in ratings input file. Entry must be an integer.";
            } else if (Double.parseDouble(entry) < 0 || Double.parseDouble(entry) > 5) {
                return "Error: Invalid entry in ratings input file. Entries must be between 1 and 5.";
            }
        }
        }

        for (String song : songs) {
        if (song == "") 
            message = "Error: Songs file includes a blank line.";
        }
        
        return message;
  
    }

    /**
     * Checks if value is double
     * @param entry
     * @return boolean
     */
    public static boolean isDouble(String entry) {
        try {
        Double.valueOf(entry);
        } catch (Exception ex) {
        return false;
        }
        return true;
    }

    /**
     * Checks if there are missing signal values
     * @param ratingsTable
     * @return boolean
     */
    public boolean missingSignalValues(ArrayList<ArrayList<String>> ratingsTable) {
    ArrayList<String> firstRow = ratingsTable.get(0);
    for (ArrayList<String> rows : ratingsTable) {
      if (fileSizesDiffer(rows, firstRow)) {
        message = "Error: Missing signal values in ratings file.";
        return true;
      }
    }
    return false;
  }
}
