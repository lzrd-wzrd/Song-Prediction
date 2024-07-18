import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.fields.FieldConversionMapping;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CS_214_Project_Tester {

  @Test
  public void blankSongsFile() throws Exception {
    String[] arguments = {"input_files/songs_blank", "input_files/ratings_readme", "output_files/output_blanksongs_songs", "output_files/output_blanksongs_users"};
    FileConverter file = new FileConverter();
    Error error = new Error();
    file.convertFileToList(arguments[0], file.songTitles);
    file.convertFileToList(arguments[1], file.songRatings);
    assertThrows(Exception.class,() -> error.handleInputFiles(file.songTitles, file.songRatings, arguments[2]));
  }
  @Test
  public void user330() {
    ArrayList<String> user1 = new ArrayList<String>() {
      {
        add("1");
        add("4");
        add("2");
      }
    };
    ArrayList<String> user2 = new ArrayList<String>() {
      {
        add("2");
        add("2");
        add("2");
      }
    };
    ArrayList<String> user3 = new ArrayList<String>() {
      {
        add("3");
        add("3");
        add("0");
      }
    };
    ArrayList<ArrayList<String>> users = new ArrayList<ArrayList<String>>() {
      {
        add(user1);
        add(user2);
        add(user3);
      }
    };
    ArrayList<ArrayList<String>> expectedUsers = new ArrayList<ArrayList<String>>() {
      {
        add(user1);
      }
    };
    Ratings rtings = new Ratings();
    rtings.removeUncoopUsers(users);
    assertEquals(expectedUsers, rtings.usersTable);
  }
  @Test
  public void calculateMeanTest() {
    ArrayList<String> ratings = new ArrayList<String>() {
      {
        add("3.0");
        add("2.0");
        add("4.0");
      }
    };
    Calc calc = new Calc();
    assertEquals(Double.parseDouble("3.0"), Double.parseDouble(calc.calculateMean(ratings,false)));
  }

  @Test
  public void calculateMeanWhenZeroInRatings() {
    ArrayList<String> ratings = new ArrayList<String>() {
      {
        add("3.0");
        add("2.0");
        add("4.0");
        add("0.0");
      }
    };
    Calc calc = new Calc();
    assertEquals(Double.parseDouble("3.0"), Double.parseDouble(calc.calculateMean(ratings,false)));
  }
  @Test
  public void calculateStdDevTest() {
    ArrayList<String> ratings = new ArrayList<String>() {
      {
        add("3.0");
        add("2.0");
        add("4.0");
      }
    };
    Calc calc = new Calc();
    assertEquals(Double.parseDouble("1.0"), Double.parseDouble(calc.calculateStdDev(3.0,ratings,false)));
  }

  @Test
  public void calculateStdDevWhenZeroRating() {
    ArrayList<String> ratings = new ArrayList<String>() {
      {
        add("3.0");
        add("2.0");
        add("4.0");
        add("0.0");
      }
    };
    Calc calc = new Calc();
    assertEquals(Double.parseDouble("1.0"), Double.parseDouble(calc.calculateStdDev(3.0,ratings,false)));
  }

  @Test
  public void undefinedStdDev() {
    ArrayList<String> ratings = new ArrayList<String>() {
      {
        add("3.0");
      }
    };
    Calc calc = new Calc();
    assertEquals("UNDEFINED", calc.calculateStdDev(3.0,ratings,false));
  }

  @Test
  public void fileIncludesBlankLine() throws Exception {
    String[] arguments = {"input_files/songs_blankline", "input_files/ratings_readme", "output_files/outputtest"};
    FileConverter file = new FileConverter();
    Error error = new Error();
    file.convertFileToList(arguments[0], file.songTitles);
    file.convertFileToList(arguments[1], file.songRatings);
    assertEquals("Error: Songs file includes a blank line.", error.handleInputFiles(file.songTitles, file.songRatings, arguments[2]));
  }

  @Test 
  public void flipTableMethod() {
    ArrayList<String> user1 = new ArrayList<String>() {
      {
        add("3.0");
        add("2.0");
      }
    };
    ArrayList<String> user2 = new ArrayList<String>() {
      {
        add("3.0");
        add("4.0");
      }
    };
    ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>() {
      {
        add(user1);
        add(user2);
      }
    };
    Ratings rtings = new Ratings();
    assertEquals(table, rtings.flipTable(rtings.flipTable(table)));
  }
  @Test
  public void uncooperativeUserRemoved() throws FileNotFoundException {
    String[] arguments = {"input_files/coop_user", "input_files/uncoop_user", "output_files/output_uncoop_user"};
    FileConverter coop = new FileConverter();
    FileConverter uncoop = new FileConverter();
    Ratings ratings = new Ratings();
    coop.convertFileToList(arguments[0], coop.songRatings);
    uncoop.convertFileToList(arguments[1], uncoop.songRatings);
    ArrayList<ArrayList<String>> cooperativeRanks = ratings.convertRatingsToTable(coop.songRatings);
    ArrayList<ArrayList<String>> cooperativeTable = ratings.flipTable(cooperativeRanks);
    ArrayList<ArrayList<String>> uncoopRanks = ratings.convertRatingsToTable(uncoop.songRatings);
    ArrayList<ArrayList<String>> uncoopTable = ratings.flipTable(uncoopRanks);
    Ratings unRatings = new Ratings();
    unRatings.removeUncoopUsers(uncoopTable);
    assertEquals(cooperativeTable, unRatings.usersTable);
  }

  private final PrintStream stdErr = System.err;
  private final ByteArrayOutputStream outputStreamGrab = new ByteArrayOutputStream();
  @BeforeEach
  public void initialize() {
    System.setErr(new PrintStream(outputStreamGrab));
  }
  @Test
  public void fileDoesNotExist() throws FileNotFoundException {
    String[] arguments = {"input_files/doesnotexist", "input_files/ratingtest", "output_files/outputtest"};
    FileConverter file = new FileConverter();
    assertThrows(FileNotFoundException.class,() -> file.convertFileToList(arguments[0], file.songTitles));
  }
  @AfterEach
  public void reset() {
    System.setErr(stdErr);
  }

  @Test
  public void getSimScore() {
    ArrayList<Double> baseUser = new ArrayList<>();
    baseUser.add(-1.0);
    baseUser.add(0.0);
    baseUser.add(1.0);
    ArrayList<Double> nextUser = new ArrayList<>();
    nextUser.add(1.0);
    nextUser.add(0.0);
    nextUser.add(-1.0);
    Similarity sim = new Similarity();
    assertEquals(-2.0, sim.getSimilarityScore(baseUser, nextUser));
  }

  @Test 
  public void missingSignalValues() throws FileNotFoundException {
    String[] missingArguments = {"input_files/songs_miss_sig", "input_files/ratings_miss_sig", "output_files/output_miss_sig"};
    FileConverter file = new FileConverter();
    Ratings ratings = new Ratings();
    Error error = new Error();
    file.convertFileToList(missingArguments[1], file.songRatings);
    ArrayList<ArrayList<String>> ratingsTable = ratings.convertRatingsToTable(file.songRatings);
    assertEquals(true, error.missingSignalValues(ratingsTable));

    String[] arguments = {"input_files/song0", "input_files/rating0", "output_files/output0"};
    FileConverter file1 = new FileConverter();
    Ratings ratings1 = new Ratings();
    Error error1 = new Error();
    file1.convertFileToList(arguments[1], file1.songRatings);
    ArrayList<ArrayList<String>> ratingsTable1 = ratings1.convertRatingsToTable(file1.songRatings);
    assertEquals(false, error1.missingSignalValues(ratingsTable1));

  }

  @Test 
  public void notMissingSignalValues() throws FileNotFoundException {
    String[] arguments = {"input_files/song0", "input_files/rating0", "output_files/output0"};
    FileConverter file = new FileConverter();
    Ratings ratings = new Ratings();
    Error error = new Error();
    file.convertFileToList(arguments[1], file.songRatings);
    ArrayList<ArrayList<String>> ratingsTable = ratings.convertRatingsToTable(file.songRatings);
    assertEquals(false, error.missingSignalValues(ratingsTable));
  }

  @Test 
  public void fileSizesDiffer() throws FileNotFoundException {
    String[] arguments = {"input_files/songs_sizes_differ", "input_files/ratings_sizes_differ", "output_files/output_size_differ"};
    FileConverter file = new FileConverter();
    Error error = new Error();
    file.convertFileToList(arguments[0], file.songTitles);
    file.convertFileToList(arguments[1], file.songRatings);
    assertEquals(true, error.fileSizesDiffer(file.songTitles, file.songRatings));
  }

  @Test 
  public void fileSizesSame() throws FileNotFoundException {
    String[] arguments = {"input_files/song0", "input_files/rating0", "output_files/output0"};
    FileConverter file = new FileConverter();
    Error error = new Error();
    file.convertFileToList(arguments[0], file.songTitles);
    file.convertFileToList(arguments[1], file.songRatings);
    assertEquals(false, error.fileSizesDiffer(file.songTitles, file.songRatings));
  }

  @Test
  public void usersWithSigValue() throws FileNotFoundException {
    String[] arguments = {"input_files/pa5_song0", "input_files/pa5_rating0", "output_files/output0"};
    FileConverter file = new FileConverter();
    Ratings ratings = new Ratings();
    Predict predict = new Predict();
    file.convertFileToList(arguments[1], file.songRatings);
    ArrayList<ArrayList<String>> ratingsTable = ratings.convertRatingsToTable(file.songRatings);
    ratings.usersTable = ratings.flipTable(ratingsTable);
    ArrayList<Integer> expected = new ArrayList<Integer>();
    expected.add(1);
    expected.add(2);
    predict.getUsersWithSigValue(ratings);
    assertEquals(expected, predict.usersWithSigValue);
  }

  @Test
  public void prepareUndefinedFile() {
    Output out = new Output();
    ArrayList<String> songs = new ArrayList<String>();
    songs.add("Roundabout");
    songs.add("Bat Country");
    ArrayList<String> expected = new ArrayList<>();
    expected.add("Roundabout UNDEFINED UNDEFINED");
    expected.add("Bat Country UNDEFINED UNDEFINED");
    out.prepareUndefinedFile(songs);
    assertEquals(expected.get(0), out.undefinedInfo.get(0));
  }

  @Test
  public void calculateDistance() {
    Song song = new Song();
    ArrayList<Double> list = new ArrayList<>();
    list.add(2.0);
    list.add(3.0);
    list.add(4.0);
    list.add(4.0);
    list.add(3.0);
    list.add(4.0);
    list.add(5.0);
    list.add(4.0);
    list.add(1.0);
    list.add(4.0);
    song.ratings = list;
    Cluster cluster = new Cluster(song);
    cluster.getCentroid().ratings = song.ratings;
    assertEquals(0.0, Cluster.calculateDistance(song, cluster));
  }

  @Test
  public void calculateCentroid() {
    ArrayList<Song> songs = new ArrayList<>();
    songs.add(new Song(new ArrayList<Double>(Arrays.asList(1.0,2.0)),"Superstition"));
    songs.add(new Song(new ArrayList<Double>(Arrays.asList(1.0,7.0)),"Higher Ground"));
    assertEquals(new Song(new ArrayList<Double>(Arrays.asList(1.0,4.5)),"").ratings,Cluster.calculateCentroid(songs).ratings);
  }

  @Test
  public void findSimilarUser() {
    ArrayList<Double> simColumn = new ArrayList<>(Arrays.asList(2.0,1.0,0.0));
    Predict p = new Predict();
    Integer simUser = p.findSimilarUser(1,simColumn);
    assertEquals(0, simUser);
  }


  @Test
  public void prepareForFile() {
    Output out = new Output();
    Song song0 = new Song();
    Song song1 = new Song();
    song0.title = "Superstition";
    song1.title = "Higher Ground";
    ArrayList<Song> songs = new ArrayList<>();
    songs.add(song0);
    songs.add(song1);

    ArrayList<String> expected = new ArrayList<>(Arrays.asList("Superstition", "Higher Ground"));

    assertEquals(expected, out.prepareForFile(songs));
  }

  @Test 
  public void getUsersMeansandStdDevs() {
    Users users = new Users();
    Users expected = new Users();
    expected.usersMeans = new ArrayList<Double>(Arrays.asList(2.5));
    expected.usersStdDevs = new ArrayList<Double>(Arrays.asList(1.2909944487));
    ArrayList<ArrayList<String>> usersTable = new ArrayList<>();
    usersTable.add(new ArrayList<String>(Arrays.asList("1","2","3","4")));
    users.getUsersMeansandStdDevs(usersTable);
    assertEquals(expected.usersMeans, users.usersMeans);
    assertEquals(expected.usersStdDevs, users.usersStdDevs);
  }

  @Test
  public void orderSongsByDistance() {
    Song song1 = new Song(new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0)),"Over the Rainbow");
    Song song2 = new Song(new ArrayList<Double>(Arrays.asList(5.0, 5.0, 5.0)),"Lost Stars");
    Song song3 = new Song(new ArrayList<Double>(Arrays.asList(1.0, 2.0, 2.0)),"Start Me Up");
    ArrayList<Song> songs = new ArrayList<>(Arrays.asList(song1, song2, song3));

    ArrayList<Song> expected = new ArrayList<Song>(Arrays.asList(song1, song3, song2));
    Cluster.initializeCluster(songs, 0);
    Cluster.clusters.get(0).setCentroid(song1);
    Cluster.orderSongsByDistance(songs, 3);

    assertEquals(expected, Cluster.clusters.get(0).getSongs());

  }
}