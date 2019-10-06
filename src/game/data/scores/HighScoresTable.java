package game.data.scores;




import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type High scores table.
 */
public class HighScoresTable implements Serializable {


    //fields
    private int maxAllowedSize;
    private List<ScoreInfo> scores;

    /**
     * Instantiates a new High scores table.
     *
     * @param size the size
     */
// Create an empty high-scores table with the specified getSize.
    // The getSize means that the table holds up to getSize top scores.
    public HighScoresTable(int size) {
        this.maxAllowedSize = size;
        this.scores = new ArrayList<>();
    }

    /**
     * Add.
     *
     * @param score the score
     */
// Add a high-score.
    public void add(ScoreInfo score) {
        //add to array
        this.scores.add(score);

        //sort the array (sorting here is upside-down!)
        Collections.sort(this.scores);

        //if after adding the array is greater than the max getSize, so last entry should be removed.
        if (this.scores.size() > this.maxAllowedSize) {
            //remove last entry
            this.scores.remove(this.scores.size() - 1);
        }
    }

    /**
     * Gets size.
     *
     * @return the size
     */
// Return currently table getSize.
    public int getSize() {
        return this.getHighScores().size();
    }

    /**
     * Gets max allowed size.
     *
     * @return the max allowed size
     */
//return maxAloowedSize
    public int getMaxAllowedSize() {
        return maxAllowedSize;
    }

    /**
     * Gets high scores.
     *
     * @return the high scores
     */
// Return the current high scores. The list is sorted such that the highest scores come first.
    public List<ScoreInfo> getHighScores() {
        return this.scores;
    }


    /**
     * Gets rank.
     *
     * @param score the score
     * @return the rank
     */
// return the rank of the current score: where will it
    // be on the list if added?
    // Rank 1 means the score will be highest on the list.
    // Rank `getSize` means the score will be lowest.
    // Rank > `getSize` means the score is too low and will not
    //      be added to the list.
    public int getRank(int score) {

        //ranks are always i+1 (because i starts from zero and rank starts from 1).
        for (int i = 0; i < this.scores.size(); i++) {
            if (score >= this.scores.get(i).getScore()) {
                //for getting the rank you need to add 1 to result
                return i + 1;
            }
        }

        //if the score is too low or the table is empty, return the very last rank.
        return this.scores.size() + 1;
    }

    /**
     * Clear.
     */
// Clears the table
    public void clear() {
        this.scores.clear();
    }

    /**
     * Load.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
// Load table data from file.
    // Current table data is cleared.
    public void load(File filename) throws IOException {
        try {
            //clear list and store all scores from file to it:
            this.clear();
            this.scores = loadFromFile(filename).getHighScores();

            //sort the list (this sort is upside-down!)
            Collections.sort(this.scores);

            //remove all elements which are beyond the permitted getSize:
            while (this.scores.size() > this.maxAllowedSize) {
                this.scores.remove(this.scores.size() - 1);
            }

            this.save(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
// Save table data to the specified file.
    public void save(File filename) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            //create object output stream
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename.getName()));

            //save table to file
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //close the stream
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
        }
    }

    /**
     * Load from file high scores table.
     *
     * @param filename the filename
     * @return the high scores table
     */
// Read a table from file and return it.
    // If the file does not exist, or there is a problem with
    // reading it, an empty table is returned.
    public static HighScoresTable loadFromFile(File filename) {
        ObjectInputStream objectInputStream = null;
        HighScoresTable highScoresTable = new HighScoresTable(0);
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(filename.getName()));
            highScoresTable = (HighScoresTable) objectInputStream.readObject();
            highScoresTable.save(filename);
            return highScoresTable;
        } catch (FileNotFoundException e) { // Can't find file to open
            System.err.println("Unable to find file: " + filename.getName());
            return highScoresTable;
        } catch (ClassNotFoundException e) { // The class in the stream is unknown to the JVM
            System.err.println("Unable to find class for object in file: " + filename.getName());
            return highScoresTable;
        } catch (IOException e) { // Some other problem
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);
            return highScoresTable;
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename.getName());
            }
        }
    }
}
