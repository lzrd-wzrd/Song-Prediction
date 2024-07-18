import java.util.ArrayList;
/**
 * Created similarity matrix for users or songs
 * @author Ian Osborn
 * @version 1.0
 */
public class Similarity {

    public ArrayList<ArrayList<Double>> usersSimMatrix;
    public ArrayList<ArrayList<Double>> songsSimMatrix;

    /**
     * Creates similarity matrix
     * @param normalizedRanksTable
     * @return similarity matrix
     */
    public ArrayList<ArrayList<Double>> getSimMatrix(ArrayList<ArrayList<Double>> normalizedRanksTable) {
        ArrayList<ArrayList<Double>> simMatrix = new ArrayList<>();
        int size = normalizedRanksTable.size();
        for (int i = 0; i < size; i++) {
            ArrayList<Double> currentUser = getUserSimScores(i,normalizedRanksTable);
            simMatrix.add(currentUser);
        }
        return simMatrix;
    }

    /**
     * Gets similarity scores for user
     * @param baseUserIndex
     * @param normalizedRanksTable
     * @return user similarity scores
     */
    public ArrayList<Double> getUserSimScores(int baseUserIndex, ArrayList<ArrayList<Double>> normalizedRanksTable) {
        ArrayList<Double> baseUser = normalizedRanksTable.get(baseUserIndex);
        ArrayList<Double> userSimScores = new ArrayList<>();
        int size = normalizedRanksTable.size();
        for (int user = 0; user < size; user++) {
            ArrayList<Double> nextUser = normalizedRanksTable.get(user);
            userSimScores.add(getSimilarityScore(baseUser, nextUser));
        }
        return userSimScores;
    }

    /**
     * Calculates sim score between two users
     * @param baseUser
     * @param nextUser
     * @return sim score
     */
    public Double getSimilarityScore(ArrayList<Double> baseUser, ArrayList<Double> nextUser) {
        Double total = 0.0;
        int size = baseUser.size();
        for (int i = 0; i < size; i++) {
            Double baseNum = baseUser.get(i);
            Double nextNum = nextUser.get(i);
            total += (baseNum * nextNum);
        }
        return total;
    }
}
