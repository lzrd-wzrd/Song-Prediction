import java.util.ArrayList;
/**
 * Calculates mean and std dev for every user
 * @author Ian Osborn
 * @version 1.0
 */
public class Users {
    public ArrayList<Double> usersMeans;
    public ArrayList<Double> usersStdDevs;
    Calc calc = new Calc();

    public Users() {
        this.usersMeans = new ArrayList<>();
        this.usersStdDevs = new ArrayList<>();
    }
    /**
     * Calculates mean and std dev for every user 
     * @param usersTable
     */
    public void getUsersMeansandStdDevs(ArrayList<ArrayList<String>> usersTable) {
        for (ArrayList<String> user : usersTable) {
            Double mean = Double.parseDouble(calc.calculateMean(user,false));
            Double stdDev = Double.parseDouble(calc.calculateStdDev(mean, user, false));
            usersMeans.add(mean);
            usersStdDevs.add(stdDev);
        }
    }
}
