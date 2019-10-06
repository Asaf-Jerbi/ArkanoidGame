package listeners;

import helpers.Counter;
import sprites.Ball;
import sprites.Block;

/**
 * The type Score tracking listener.
 */
public class ScoreTrackingListener implements HitListener {

    private Counter currentScore;

    /**
     * Instantiates a new Score tracking listener.
     *
     * @param scoreCounter the score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * @param beingHit the being hit
     * @param hitter   the hitter
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        //hit is going to delete block so give user score:
        if (beingHit.getHitPoints() >= 1) {
            this.currentScore.increase(5);
        } else if (beingHit.getHitPoints() == 0) {
            this.currentScore.increase(10);
        } else {
            System.out.println("ERROR: hit numbers is negative, the value:" + beingHit.getHitPoints());
        }
    }
}