package interfaces;

import sprites.Block;

/**
 * a BlockCreator.
 */
public interface BlockCreator {


    /**
     * Create block.
     *
     * @param xPosition the x position
     * @param yPosition the y position
     * @return the block
     */
    Block create(int xPosition, int yPosition);
}
