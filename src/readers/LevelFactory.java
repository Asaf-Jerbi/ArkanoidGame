package readers;


import game.LevelInformation;
import game.Velocity;
import sprites.Block;
import sprites.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * a LevelFactory class.
 */
public class LevelFactory implements LevelInformation {


    //fields
    private List<Velocity> initialBallVelocities;
    private List<Object> blocksProperties;
    private String levelName;
    private Sprite background;
    private int numberOfBalls;
    private int numberOfBlocksToRemove;
    private int paddleSpeed;
    private int paddleWidth;


    /**
     * Instantiates a new Level factory.
     *
     * @param initialBallVelocities the initial ball velocities
     * @param paddleSpeed           the paddle speed
     * @param paddleWidth           the paddle width
     * @param levelName             the level name
     * @param background            the background
     * @param blocksProperties      the blocks properties
     * @param numBlocks             the num blocks
     */
//Constructor
    public LevelFactory(List<Velocity> initialBallVelocities, int paddleSpeed, int paddleWidth, String levelName,
                        Sprite background, List<Object> blocksProperties, int numBlocks) {
        this.initialBallVelocities = initialBallVelocities;
        this.blocksProperties = new ArrayList<>(blocksProperties);
        this.levelName = levelName;
        this.background = background;
        this.numberOfBalls = this.initialBallVelocities.size();
        this.numberOfBlocksToRemove = numBlocks;
        this.paddleWidth = paddleWidth;
        this.paddleSpeed = paddleSpeed;
    }


    /**
     * num of balls.
     *
     * @return int
     */
    public int numberOfBalls() {
        return this.numberOfBalls;
    }

    /**
     * init ball's velocities.
     *
     * @return list
     */
    public List<Velocity> initialBallVelocities() {
        return this.initialBallVelocities;
    }

    /**
     * paddlr speed.
     *
     * @return int
     */
    public int paddleSpeed() {
        return this.paddleSpeed;
    }


    /**
     * paddle width.
     *
     * @return int
     */
    public int paddleWidth() {
        return this.paddleWidth;
    }


    /**
     * level name.
     *
     * @return string
     */
    public String levelName() {
        return this.levelName;
    }


    /**
     * get background.
     *
     * @return sprite
     */
    public Sprite getBackground() {
        return this.background;
    }

    /**
     * blocks.
     *
     * @return list
     */
    public List<Block> blocks() {
        return createBlocks(this.blocksProperties);
    }


    /**
     * number of blocks.
     *
     * @return int
     */
    public int numberOfBlocksToRemove() {
        return this.numberOfBlocksToRemove;
    }


    /**
     * Create blocks list.
     *
     * @param blockProperties the block properties
     * @return the list
     */
    public List<Block> createBlocks(List<Object> blockProperties) {

        List<String> blocksInfo = (List<String>) blockProperties.get(3);
        BlocksFromSymbolsFactory bfsf = (BlocksFromSymbolsFactory) blockProperties.get(4);

        List<Block> blocks = new ArrayList<>();

        int currHeight = (int) blockProperties.get(2);
        int currWidth;

        for (int i = 0; i < blocksInfo.size(); i++) {
            currWidth = (int) blockProperties.get(1);
            for (int j = 0; j < blocksInfo.get(i).length(); j++) {

                char e = blocksInfo.get(i).charAt(j);

                if (bfsf.isBlockSymbol(e + "")) {
                    Block newB = bfsf.getBlock(e + "", currWidth, currHeight);
                    blocks.add(newB);
                    currWidth = currWidth + (int) newB.getCollisionRectangle().getWidth();
                } else if (bfsf.isSpaceSymbol(e + "")) {
                    currWidth = currWidth + (bfsf.getSpaceWidth(e + ""));
                } else {
                    throw new RuntimeException("bad symbol " + e);
                }

            }
            currHeight = currHeight + (int) blockProperties.get(0);
        }
        return blocks;
    }

}
