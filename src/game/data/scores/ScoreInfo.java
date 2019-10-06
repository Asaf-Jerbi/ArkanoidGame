package game.data.scores;

import java.io.Serializable;

/**
 * The type Score info.
 */
public class ScoreInfo implements Serializable, Comparable<ScoreInfo> {

    private String name;
    private int score;

    /**
     * Instantiates a new Score info.
     *
     * @param name  the name
     * @param score the score
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return this.score;
    }

    @Override
    public int compareTo(ScoreInfo o) {
        //this implementation made to support upside-down order (as high-scores table should be).
        if (o.getScore() > this.getScore()) {
            return 1;
        } else if (o.getScore() < this.getScore()) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "game.data.scores.ScoreInfo{"
                + "name='" + name + '\''
                + ", score=" + score
                + '}';
    }
}
