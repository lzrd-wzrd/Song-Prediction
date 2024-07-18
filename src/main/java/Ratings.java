import java.util.ArrayList;
/**
 * Handles processes with user ratings
 * @author Ian Osborn
 * @version 1.0
 */

public class Ratings {

    public String errorMessage;
    public ArrayList<ArrayList<String>> songsTable;
    public ArrayList<ArrayList<String>> usersTable;
    public ArrayList<ArrayList<Double>> normRanksTable;

    public Ratings() {
      this.songsTable = new ArrayList<>();
      this.usersTable = new ArrayList<>();
      this.normRanksTable = new ArrayList<>();
    }

    /**
     * Converts ratings to table
     * @param songRatings
     * @return table of converted ratings
     */
    public ArrayList<ArrayList<String>> convertRatingsToTable(ArrayList<String> songRatings) {
    ArrayList<ArrayList<String>> usersRatings = new ArrayList<>();

    for (String str : songRatings) {
      String[] ratingsForSong = str.split(" ");
      ArrayList<String> ratings = new ArrayList<>();

      for (String rating : ratingsForSong) { 
        try {
          int checkInt = Integer.parseInt(rating);
          Double checkDouble = Double.parseDouble(rating);
          ratings.add(rating);
        } catch (NumberFormatException e) {
          errorMessage = "Error: File must only contain integers and whitespaces";
          System.err.println(errorMessage);
          ratings.set(0,errorMessage);
          if (usersRatings.size() == 0)
            usersRatings.add(ratings);
          else
            usersRatings.set(0,ratings);
          return usersRatings;
        }
      }
      usersRatings.add(ratings);
    }
    return usersRatings;
  }

    /**
     * Flips the ratings table to make it easier to work with based on user or song
     * @param ratingsTable
     * @return flipped table
     */
    public ArrayList<ArrayList<String>> flipTable(ArrayList<ArrayList<String>> ratingsTable) {
        ArrayList<ArrayList<String>> uTable = new ArrayList<>();
        int user = 0;
        while (user < ratingsTable.get(0).size()) {
          ArrayList<String> currentUser = new ArrayList<>();
          for (int i = 0; i < ratingsTable.size(); i++) {
            currentUser.add(ratingsTable.get(i).get(user));
          }
          uTable.add(currentUser);
          user++;
        }
        return uTable; 
    }

    /**
     * Flips the prediction table to make it easier to work with based on user or song
     * @param predict
     * @return flipped table
     */
    public ArrayList<ArrayList<Double>> flipTable(Predict predict) {
        ArrayList<ArrayList<Double>> uTable = new ArrayList<>();
        int user = 0;
        while (user < predict.predictedUsersTable.get(0).size()) {
          ArrayList<Double> currentUser = new ArrayList<>();
          for (int i = 0; i < predict.predictedUsersTable.size(); i++) {
            currentUser.add(predict.predictedUsersTable.get(i).get(user));
          }
          uTable.add(currentUser);
          user++;
        }
        return uTable; 
    }

    /**
     * Removes uncooperative users from users table
     * @param usersTable
     */
    public void removeUncoopUsers(ArrayList<ArrayList<String>> usersTable) {
        boolean cooperativeUser = false;
        int numUsers = usersTable.size();
        ArrayList<ArrayList<String>> newUsersTable = new ArrayList<>();

        for (int j = 0; j < numUsers; j++) {
          String priorRating = "0"; 
          for (String num : usersTable.get(j)) {
            if (!num.equals("0")) {
              priorRating = num.toString();
              break;
            }
          }
          cooperativeUser = false;
          int start = numUsers == 1 ? 0 : 1;
          for (int i = start; i < usersTable.get(j).size(); i++) {
            String currentRating = usersTable.get(j).get(i);
            if (Integer.parseInt(currentRating) != Integer.parseInt(priorRating) & Integer.parseInt(currentRating) != 0) {
              cooperativeUser = true;
              break;
            } 
          }
          
          if (cooperativeUser == true) {
            newUsersTable.add(usersTable.get(j));
          }
        } 
        this.usersTable = newUsersTable;
    }

    /**
     * Gets normalized ranks table from users means and users std devs
     * @param usersMeans
     * @param usersStdDevs
     */
    public void getNormRanksTable(ArrayList<Double> usersMeans, ArrayList<Double> usersStdDevs) {
        for (int i = 0; i < usersTable.size(); i++) {
        ArrayList<String> user = usersTable.get(i);
        ArrayList<Double> userNormRanks = new ArrayList<>();
        for (int j = 0; j < user.size(); j++) {
          Double normRank = 0.0;
          Double rank = Double.parseDouble(user.get(j));
          if (rank == 0.0) {
            normRank = 0.0;
          } else {
            normRank = (rank - usersMeans.get(i)) / usersStdDevs.get(i);
          }
          userNormRanks.add(normRank);
        }
        normRanksTable.add(userNormRanks);
      }
    }
}
