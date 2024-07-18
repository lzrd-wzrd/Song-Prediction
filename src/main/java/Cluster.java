import java.util.ArrayList;
import java.util.Arrays;
/**
 * Handles all processes for k-means clustering
 * @author Ian Osborn
 * @version 1.0
 */
public class Cluster {
    private Song centroid;
    private ArrayList<Song> clusterSongs;
    static ArrayList<Cluster> clusters = new ArrayList<>();

    public Cluster(Song centroid) {
        this.centroid = centroid;
        this.clusterSongs = new ArrayList<Song>();
    }
    public Cluster() {
        this.clusterSongs = new ArrayList<Song>();
    }
    /**
     * Sets initial clusters
     * @param songs
     * @param k
     */
    public static void initializeCluster(ArrayList<Song> songs, int k) {
        Song centroid = songs.get(k);
        Cluster cluster = new Cluster(centroid);
        clusters.add(cluster);
    }
    
    public static void orderSongsByDistance(ArrayList<Song> songs, int radioSize) {
        double[] songDistances = new double[songs.size()];
        int i = 0;
        for (Song song : songs) {
            for (Cluster cluster : clusters) {
                song.distanceFromCentroid = calculateDistance(song, cluster); 
                songDistances[i] = song.distanceFromCentroid;             
            }
            i++;
        }

        Arrays.sort(songDistances);
        
        for (int j = 0; j < radioSize; j++) {
            for (Song song : songs) {
                if (clusters.get(0).clusterSongs.size() >= radioSize) {
                    break;
                }
                if (song.distanceFromCentroid == songDistances[j]) {
                    clusters.get(0).clusterSongs.add(song);
                    //System.out.println(song.distanceFromCentroid);
                }
            }
        }

    }
    /**
     * Assigns songs to clusters
     * @param songs
     * @param k
     */
    public static void assignToCluster(ArrayList<Song> songs, int k) {
        for (Song song : songs) {
            Double minDistance = 1000.0;
            Cluster closestCluster = null;
            for (Cluster cluster : clusters) {
                double distance = calculateDistance(song, cluster);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestCluster = cluster;
                }
            }
            for (Cluster cluster : clusters) {
                if (cluster == closestCluster) {
                    cluster.clusterSongs.add(song);
                }
            }
        }
        
    }
    /**
     * Updates centroids of clusters
     * @param maxIterations
     */
    public static void updateCentroids(int maxIterations) {
        for (Cluster cluster: clusters) {
            if (!cluster.getSongs().isEmpty()) {
                Song newCentroid = calculateCentroid(cluster.getSongs());
                newCentroid.title = cluster.centroid.title;
                cluster.centroid = newCentroid;
                if (maxIterations != 9) {cluster.clusterSongs.clear();};
            }
        }
    }
    /**
     * Calculates distance between two song objects
     * @param songA
     * @param songB
     * @return
     */
    public static double calculateDistance(Song songA, Cluster songB) {
        Double distance = 0.0;
        for (int i = 0; i < songA.ratings.size(); i++) {
            Double aMinusB = songA.ratings.get(i) - songB.centroid.ratings.get(i);
            distance += Math.pow(aMinusB, 2);
        }
        return Math.sqrt(distance);
    }
    /**
     * Calculates centroid given a list of song objects
     * @param songs
     * @return
     */
    public static Song calculateCentroid(ArrayList<Song> songs) {
        Song thisCentroid = new Song();
        for (int i = 0; i < songs.get(0).ratings.size(); i++) {
            Double average = 0.0;
            Double total = 0.0;
            for (int j = 0; j < songs.size(); j++) {
                total += songs.get(j).ratings.get(i);
            }
            average = total / songs.size();
            thisCentroid.ratings.add(average);
        }
        return thisCentroid;
    }

    public Song getCentroid() {
        return centroid;
    }

    public void setCentroid(Song centroid) {
        this.centroid = centroid;
    }

    public ArrayList<Song> getSongs() {
        return clusterSongs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.clusterSongs = songs;
    }
    public static ArrayList<Cluster> getClusters() {
        return clusters;
    }
}
