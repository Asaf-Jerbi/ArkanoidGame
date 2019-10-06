package listeners;

import game.GameLevel;
import helpers.Counter;
import sprites.Ball;
import sprites.Block;

/**
 * The type Ball remover.
 */
public class BallRemover implements HitListener {

    //fields
    private GameLevel gameLevel;
    private Counter remainingBalls;

    /**
     * Instantiates a new Ball remover.
     *
     * @param gameLevel           the gameLevel
     * @param remainingBalls the remaining balls
     */
//Constructor
    public BallRemover(GameLevel gameLevel, Counter remainingBalls) {
        this.gameLevel = gameLevel;
        if (remainingBalls.getValue() >= 0) {
            this.remainingBalls = remainingBalls;
        } else {
            System.out.println("number of balls can't be negative");
        }
    }


    /**
     * balls that hit the bottom of the screen should be removed.
     *
     * @param beingHit the block
     * @param hitter the ball
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.gameLevel);
        this.remainingBalls.decrease(1);
//        if (remainingBalls.getValue() == 0) {
//            beingHit.removeHitListener(this);
//        }
    }

}
