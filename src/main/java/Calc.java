import java.text.DecimalFormat;
import java.util.ArrayList;
/**
 * Provides methods to calculate mean and std dev
 * @author Ian Osborn
 * @version 1.0
 */
public class Calc {
    /**
     * Calculates mean of list
     * @param numbers
     * @param norm
     * @return result, which is mean of numbers 
     */
    public String calculateMean(ArrayList<String> numbers, boolean norm) {
        double decider = norm ? 1000.0 : 0.0;
        double total = 0;
        int lengthOfArray = numbers.size();

        for (int i = 0; i < numbers.size(); i++) {
            double num = Double.parseDouble(numbers.get(i));
            if (num == decider)
            lengthOfArray -= 1;
            else {
            total += num;
            }
        }

        DecimalFormat df = new DecimalFormat("#.##########");
        String result = df.format(total / lengthOfArray);
        return lengthOfArray > 0 ? result : "UNDEFINED";
    }
    /**
     * Calculates the std dev of list of numbers
     * @param mean
     * @param numbers
     * @param norm
     * @return std dev
     */
    public String calculateStdDev(double mean, ArrayList<String> numbers, boolean norm) {
        double decider = norm ? 1000.0 : 0.0;
        int lengthOfArray = numbers.size();
        if (numbers.size() < 2) {
        return "UNDEFINED";
        } else {
        double workingNumber = 0;
        for (int i = 0; i < numbers.size(); i++) {
            double number = Double.parseDouble(numbers.get(i));
            if (lengthOfArray == 0) 
            return "UNDEFINED";
            if (number == decider) {
            lengthOfArray -= 1;
            } else {
            workingNumber += Math.pow(number - mean, 2);
            }
        }
        DecimalFormat df = new DecimalFormat("#.##########");
        String result = lengthOfArray <= 1 ? "UNDEFINED" : df.format(Math.sqrt(workingNumber / (lengthOfArray - 1)));
        return result;
        }
    }
}
