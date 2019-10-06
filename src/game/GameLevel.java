package game;

import biuoop.KeyboardSensor;
import game.animations.Animation;
import game.animations.AnimationRunner;
import game.animations.CountdownAnimation;
import game.animations.KeyPressStoppableAnimation;
import game.animations.PauseScreen;
import helpers.Counter;
import helpers.Helper;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;
import biuoop.DrawSurface;
import geometry.Point;
import sprites.Paddle;
import sprites.Ball;
import sprites.Block;
import sprites.Sprite;
import sprites.ScoreIndicator;
import sprites.Collidable;
import sprites.LivesIndicator;
import sprites.LevelIndicator;

import java.awt.Color;
import java.util.List;

/**
 * The type game.GameLevel.
 */
public class GameLevel implements Animation {

    //fields

    private LevelInformation levelInformation;
    private SpriteCollection sprites;
    private GameEnvironment gameEnvironment;
    private Paddle paddle;
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter gameScore;
    private Counter numberOfLives;
    private static final int WIDTH = 800, HEIGHT = 600;
    private AnimationRunner animationRunner;
    private boolean running;
    private KeyboardSensor keyboard;


    //******************************************************************************************
    //Constructor
    //******************************************************************************************

    /**
     * Instantiates a new game.GameLevel.
     *
     * @param levelInformation the level information
     * @param ks               the ks
     * @param ar               the ar
     * @param gameScore        the game score
     * @param lives            the lives
     */
    public GameLevel(LevelInformation levelInformation, KeyboardSensor ks, AnimationRunner ar, Counter gameScore,
                     Counter lives) {
        this.levelInformation = levelInformation;
        this.animationRunner = ar;
        this.keyboard = ks;
        this.sprites = new SpriteCollection();
        this.gameEnvironment = new GameEnvironment();
        this.remainingBlocks = new Counter(0);
        this.remainingBalls = new Counter(0);
        this.gameScore = gameScore;
        this.numberOfLives = lives;
    }


    //******************************************************************************************
    //Getters:
    //******************************************************************************************

    /**
     * Gets level information.
     *
     * @return the level information
     */
    public LevelInformation getLevelInformation() {
        return levelInformation;
    }

    /**
     * Gets sprites.
     *
     * @return the sprites
     */
    public SpriteCollection getSprites() {
        return sprites;
    }

    /**
     * Gets environment.
     *
     * @return the environment
     */
    public GameEnvironment getGameEnvironment() {
        return gameEnvironment;
    }

    /**
     * Gets keyboard.
     *
     * @return the keyboard
     */
    public KeyboardSensor getKeyboard() {
        return keyboard;
    }

    /**
     * Gets remaining blocks.
     *
     * @return the remaining blocks
     */
    public Counter getRemainingBlocks() {
        return remainingBlocks;
    }


    /**
     * Gets remaining balls.
     *
     * @return the remaining balls
     */
    public Counter getRemainingBalls() {
        return remainingBalls;
    }


    /**
     * Add collidable.
     *
     * @param c the c
     */
    public void addCollidable(Collidable c) {
        this.gameEnvironment.getCollidables().add(c);
    }

    /**
     * Gets animation runner.
     *
     * @return the animation runner
     */
    public AnimationRunner getAnimationRunner() {
        return this.animationRunner;
    }


    //******************************************************************************************
    //methods:
    //******************************************************************************************

    /**
     * Add sprite.
     *
     * @param s the s
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initialize a new game: create the Blocks and sprites.Ball (and sprites.Paddle).
     * and add them to the game.
     */
    public void initialize() {

        //create the background:
        this.levelInformation.getBackground().addToGame(this);

        //create the paddle:
        this.paddle = new Paddle(new Block(400 - 0.5 * this.levelInformation.paddleWidth(), 570,
                this.levelInformation.paddleWidth(), 19), this.levelInformation.paddleSpeed(), this);
        this.paddle.addToGame(this);

        //add score indicator:
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.gameScore);
        scoreIndicator.addToGame(this);

        //add lives indicator:
        LivesIndicator livesIndicator = new LivesIndicator(this.numberOfLives);
        livesIndicator.addToGame(this);

        //add level's name indicator:
        LevelIndicator levelIndicator = new LevelIndicator(this.levelInformation.levelName());
        levelIndicator.addToGame(this);

        //add game frames:
        setFrames(new Point(0, 20), WIDTH, HEIGHT - scoreIndicator.getHeight(), 10);

        //add the blocks:
        createTheBlocks();
    }

    /**
     * Create the blocks.
     */
    private void createTheBlocks() {
        for (Block b : this.levelInformation.blocks()) {

            //add each block to the game (to collidables and sprites)
            b.addToGame(this);
            this.remainingBlocks.increase(1);
            //add listeners to each block:
            //listener 1: score listener the the block will notify when it was hit.
            //listener 2: remover listener which will be notified by the block, so it would know
            // if block should be removed (depending how many hits remain for the block).

            b.addHitListener(new ScoreTrackingListener(this.gameScore));
            b.addHitListener(new BlockRemover(this, this.remainingBlocks));
        }
    }

    /**
     * Run the game -- start the animations loop.
     *
     */
    public void playOneTurn() {
        this.createTheBalls();
        this.animationRunner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        this.animationRunner.run(this);
    }

    /**
     * Sets frames to game.
     *
     * @param upperLeft      the upper left point of the game screen
     * @param width          the width of the game screen
     * @param height         the height of the game screen
     * @param frameThickness the frame thickness
     */
    private void setFrames(Point upperLeft, double width, double height, int frameThickness) {
        //locals
        Point upperRight = Helper.calcUpperRight(upperLeft, width, height);
        Point downLeft = Helper.calcDownLeft(upperLeft, width, height);
        Point downRight = Helper.calcDownRight(upperLeft, width, height);

        //crating frame borders as blocks:
        Block upFrameBorder = new Block(upperLeft, width, frameThickness, Color.DARK_GRAY); //V
        Block leftFrameBorder = new Block(upperLeft, frameThickness, height, Color.DARK_GRAY); // V
        Block rightFrameBorder = new Block(new Point(upperRight.getX() - frameThickness, upperRight.getY()),
                frameThickness, height, Color.DARK_GRAY); //V
        //the "death region" block
        Block downFrameBorder = new Block(new Point(downLeft.getX(), downLeft.getY() - frameThickness),
                width, frameThickness, Color.DARK_GRAY); //V

        //add to game:
        upFrameBorder.addToGame(this);
        leftFrameBorder.addToGame(this);
        rightFrameBorder.addToGame(this);
        downFrameBorder.addToGame(this);
        //add listener to the down frame so it would know to delete balls which hit it:
        downFrameBorder.addHitListener(new BallRemover(this, this.remainingBalls));
    }

    /**
     * Center the paddle.
     */
    private void centerThePaddle() {
        this.paddle.removeFromGame(this);
        this.paddle = new Paddle(new Block(400 - 0.5 * this.levelInformation.paddleWidth(), 570,
                this.levelInformation.paddleWidth(), 19), this.levelInformation.paddleSpeed(), this);
        this.paddle.addToGame(this);
    }

    /**
     * Create two default balls.
     */
    private void createTheBalls() {
        List<Velocity> ballVelocities = this.levelInformation.initialBallVelocities();

        for (Velocity v : ballVelocities) {
            Ball b = new Ball(400, 560, 8);
            b.setVelocity(v);
            b.addToGame(this);
            this.remainingBalls.increase(1);
        }
    }


    //******************************************************************************************
    //removers
    //******************************************************************************************

    /**
     * Remove collidable.
     *
     * @param c the c
     */
    public void removeCollidable(Collidable c) {
        this.gameEnvironment.getCollidables().remove(c);
    }

    /**
     * Remove sprite.
     *
     * @param s the s
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }


    @Override
    public void doOneFrame(DrawSurface d) {
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();

        if (this.keyboard.isPressed("p")) {
            this.animationRunner.run(new KeyPressStoppableAnimation(new PauseScreen(), this.keyboard, "space"));
        }
    }

    @Override
    public boolean shouldStop() {

        if (this.remainingBlocks.getValue() == 0) {
            this.running = false;
            this.gameScore.increase(100);
        } else if (this.remainingBalls.getValue() == 0) {
            this.centerThePaddle();
            this.running = false;
            this.numberOfLives.decrease(1);
        }

        return !this.running;
    }


    /**
     * Gets lives.
     *
     * @return the lives
     */
    public Counter getLives() {
        return this.numberOfLives;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public Counter getScore() {
        return this.gameScore;
    }
}