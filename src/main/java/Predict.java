import java.util.ArrayList;
/**
 * Handles prediction processes and creates a new table with predicted rankings
 * @author Ian Osborn
 * @version 1.0
 */
public class Predict {

    ArrayList<Integer> usersWithSigValue;
    ArrayList<ArrayList<Double>> predictedUsersTable;

    public Predict() {
        this.usersWithSigValue = new ArrayList<>();
        this.predictedUsersTable = new ArrayList<>();
    }

    /**
     * Gets users that have signal values
     * @param ratings
     */
    public void getUsersWithSigValue(Ratings ratings) {
        for (ArrayList<String> user : ratings.usersTable) {
            for (String value : user) {
                if (Double.parseDouble(value) == 0.0) {
                    usersWithSigValue.add(ratings.usersTable.indexOf(user));
                    break;
                }
            }
        }
    }

    /**
     * Finds similar users to the users with signal values
     * @param sigUser
     * @param simColumn
     * @return index of simUser
     */
    public Integer findSimilarUser(Integer sigUser, ArrayList<Double> simColumn) {
        Integer simUser = 0;
        Double maxSim = 0.0;
        for (Double simScore : simColumn) {
            if (simColumn.indexOf(simScore) != sigUser && maxSim == 0.0) {
                maxSim = simScore;
            }
            else if (simColumn.indexOf(simScore) != sigUser) {
                if (simScore > maxSim) {
                    simUser = simColumn.indexOf(simScore);
                    maxSim = simScore;
                }
            }
        }
        return simUser;
    }

    /**
     * Replaces signal values with predicted values and updates predicted table
     * @param ratings
     * @param sim
     * @param users
     */
    public void replaceSigValue(Ratings ratings, Similarity sim, Users users) {
        predictedUsersTable = ratings.normRanksTable;
        for (Integer user : usersWithSigValue) {
            ArrayList<Double> simColumn = sim.usersSimMatrix.get(user);
            Integer simUser = findSimilarUser(user, simColumn);
            for (String value : ratings.usersTable.get(user)) {
                if (Double.parseDouble(value) == 0) {
                    Double predictedNormValue = ratings.normRanksTable.get(simUser).get(ratings.usersTable.get(user).indexOf(value));
                    Double predictedRawValue = (predictedNormValue * users.usersStdDevs.get(user)) + users.usersMeans.get(user);
                    Double prediction = (double) Math.round(predictedRawValue);
                    Double predictedValue = prediction > 5 ? 5 : prediction;
                    predictedUsersTable.get(user).set(predictedUsersTable.get(user).indexOf(value),predictedValue);
                }
            }
        }
        predictedUsersTable = ratings.flipTable(this);
    }

    /**
     * Replaces signal values with normalized predicted values and updates predicted table
     * @param ratings
     * @param sim
     * @param users
     */
    public void replaceNormSigValue(Ratings ratings, Similarity sim, Users users) {
        predictedUsersTable = ratings.normRanksTable;
        for (Integer user : usersWithSigValue) {
            ArrayList<Double> simColumn = sim.usersSimMatrix.get(user);
            Integer simUser = findSimilarUser(user, simColumn);
            for (Double value : ratings.normRanksTable.get(user)) {
                if (value == 0) {
                    Double predictedNormValue = ratings.normRanksTable.get(simUser).get(ratings.normRanksTable.get(user).indexOf(value));
                    int index = ratings.normRanksTable.get(user).indexOf(value);
                    predictedUsersTable.get(user).set(index,predictedNormValue);
                }
            }
        }
        predictedUsersTable = ratings.flipTable(this);
    }
}
