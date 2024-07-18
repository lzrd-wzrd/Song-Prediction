import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
/**
 * Handles processes for outputing information
 * @author Ian Osborn
 * @version 1.0
 */
public class Output {

    public String[] songInfo;
    public ArrayList<String> userInfo;
    public ArrayList<String> undefinedInfo;

    /**
     * Prepares data for file output
     * @param songs
     * @return list that's formatted for output
     */
    public ArrayList<String> prepareForFile(ArrayList<Song> songs) {
        ArrayList<String> titles = new ArrayList<String>();
        for (Song song : songs) {
            titles.add(song.title);
        }   
        ArrayList<String> output = (ArrayList<String>) titles.stream().distinct().collect(Collectors.toList());        
        return output;
    }

    /**
     * Prepares data for undefined output file
     * @param songTitles
     */
    public void prepareUndefinedFile(ArrayList<String> songTitles) {
        undefinedInfo = new ArrayList<>();
        for (int i = 0; i < songTitles.size(); i++) {
          String songConcat = songTitles.get(i) + " UNDEFINED" + " UNDEFINED";
          undefinedInfo.add(songConcat);
        }
    }

    /**
     * Takes formatted list of data and writes to file
     * @param arg
     * @param info
     * @throws IOException
     */
    public void writeToFile(String arg, ArrayList<String> info) throws IOException {
        FileWriter fileWriter = new FileWriter(arg);
        try {
            for (String str : info) {
                fileWriter.write(str + "\n");
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            fileWriter.close();
        }
  }
}
