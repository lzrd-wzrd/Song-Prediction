import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Main 
 * @author Ian Osborn
 * @version 1.0
 */
public class CS_214_Project {
  private static int K = 0;
  private static int songIndex = 0;
  private static boolean shuffle;
  
  /**
   * Runs program
   * @param args
   */
  public static void main(String[] args) {
    long start = System.currentTimeMillis();

    if (args.length != 5) {
      System.err.println("Error: incorrect number of arguments.");
      System.exit(0);
    }
    if (isInteger(args[3]) == false) {
      System.err.println("Error: N must be an integer.");
      System.exit(0);
    }

    songIndex = Integer.parseInt(args[3]);
    String shuffleInput = args[4].toLowerCase();
    if (shuffleInput.equals("true")) {
      shuffle = true;
    } else if (shuffleInput.equals("false")) {
      shuffle = false;
    } else {
      System.err.println("Error: The final argument must be true or false");
      System.exit(0);
    }
    
    if (songIndex < 1) {
      System.err.println("Error: Song index cannot be less than 1.");
      System.exit(0);
    }

    FileConverter fileConverter = new FileConverter();
    Output output = new Output();
    try {
      fileConverter.convertFileToList(args[0], fileConverter.songTitles);
      fileConverter.convertFileToList(args[1], fileConverter.songRatings);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    Error error = new Error();
    if (error.fileSizesDiffer(fileConverter.songTitles, fileConverter.songRatings)) {
      System.err.println(error.message);
      System.exit(0);
    }

    String errorMessage = " ";
    try {
      errorMessage = error.handleInputFiles(fileConverter.songTitles, fileConverter.songRatings,args[2]);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (errorMessage == "Ratings File Blank") {
      System.exit(0);
    } else if (errorMessage != " ") {
      System.err.println(errorMessage);
      System.exit(0);
    }
    if (fileConverter.songTitles.size() < 100) {
      System.err.println("Error: Songs and ratings files must contain at least 100 lines.");
      System.exit(0);
    }

    if (songIndex > fileConverter.songTitles.size()) {
      System.err.println("Error: Song index cannot be greater than the size of the songs or ratings file.");
      System.exit(0);
    }

    Ratings ratings = new Ratings();
    ratings.songsTable = ratings.convertRatingsToTable(fileConverter.songRatings);
    if (error.missingSignalValues(ratings.songsTable)) {
      System.err.println(error.message);
      System.exit(0);
    }

    if (ratings.songsTable.get(0).get(0) == "Error: File must only contain integers and whitespaces")
        System.exit(0);

    ratings.usersTable = ratings.flipTable(ratings.songsTable);
    ratings.removeUncoopUsers(ratings.usersTable);
    if (ratings.usersTable.size() == 0) {
      output.prepareUndefinedFile(fileConverter.songTitles);
      try {
        output.writeToFile(args[2], output.undefinedInfo);
      } catch (IOException e) {

      }
      System.exit(0);
    }

    Users users = new Users();
    users.getUsersMeansandStdDevs(ratings.usersTable);
    ratings.getNormRanksTable(users.usersMeans,users.usersStdDevs);

    Similarity sim = new Similarity();
    sim.usersSimMatrix = sim.getSimMatrix(ratings.normRanksTable);
    
    Predict predict = new Predict();
    predict.getUsersWithSigValue(ratings);
    predict.replaceNormSigValue(ratings, sim, users);

    ArrayList<ArrayList<Double>> songsNormTable = predict.predictedUsersTable;
    
    ArrayList<Song> songs = new ArrayList<>();
    for (int i = 0; i < songsNormTable.size(); i++) {
      Song song = new Song(songsNormTable.get(i), fileConverter.songTitles.get(i));
      songs.add(song);
    }

    K = songIndex - 1 ;
    Cluster.initializeCluster(songs, K);
    int radioSize = 50;
    Cluster.orderSongsByDistance(songs, radioSize);
    
    ArrayList<Song> radioSongs = Cluster.getClusters().get(0).getSongs();
    if (shuffle) {
      Collections.shuffle(radioSongs);
    }
    output.userInfo = output.prepareForFile(radioSongs);
        
    if (errorMessage != " ") {
      System.exit(0);
    } else {
      try {
        output.writeToFile(args[2], output.userInfo);
      } catch (IOException e) {
        e.printStackTrace();
      } 
    }

    long end = System.currentTimeMillis();
    //System.out.println(end - start);
  }
  /**
   * Checks if value is an integer
   * @param args
   * @return boolean
   */
  public static boolean isInteger(String args) {
        try {
          Integer.valueOf(args);
        } catch (Exception ex) {
          return false;
        }
        return true;
    }
}
