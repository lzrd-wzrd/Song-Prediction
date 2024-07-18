import java.util.ArrayList;
/**
 * Creates song objects
 * @author Ian Osborn
 * @version 1.0
 */
public class Song {
    public ArrayList<Double> ratings;
    public String title;
    public double distanceFromCentroid;

    /**
     * Creates song object
     * @param ratings
     * @param title
     */
    public Song(ArrayList<Double> ratings, String title) {
        this.ratings = ratings;
        this.title = title;
    }

    public Song() {
        this.ratings = new ArrayList<>();
        this.title = new String();
    }
}
