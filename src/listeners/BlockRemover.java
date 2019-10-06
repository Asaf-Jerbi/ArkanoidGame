package listeners;
// a listeners.BlockRemover is in charge of removing blocks from the gameLevel, as well as keeping count
// of the number of blocks that remain.

import game.GameLevel;
import helpers.Counter;
import sprites.Ball;
import sprites.Block;

/**
 * The type Block remover.
 */
public class BlockRemover implements HitListener {

    //fields
    private GameLevel gameLevel;
    private Counter remainingBlocks;

    /**
     * Instantiates a new Block remover.
     *
     * @param gameLevel            the gameLevel
     * @param remainingBlocks the remaining blocks
     */
//Constructor
    public BlockRemover(GameLevel gameLevel, Counter remainingBlocks) {
        this.gameLevel = gameLevel;
        if (remainingBlocks.getValue() >= 0) {
            this.remainingBlocks = remainingBlocks;
        } else {
            System.out.println("number of blocks can't be negative");
        }
    }


    /**
     * Blocks that are hit and reach 0 hit-points should be removed.
     * from the gameLevel. Remember to remove this listener from the block
     * that is being removed from the gameLevel.
     *
     * @param beingHit the block
     * @param hitter   the ball
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeFromGame(gameLevel);
            beingHit.removeHitListener(this);
            this.remainingBlocks.decrease(1);
        }
    }
}