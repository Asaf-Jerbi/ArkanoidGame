package game.animations;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import game.SpriteCollection;
import helpers.Counter;

import java.awt.Color;

// The CountdownAnimation will display the given gameScreen,
// for numOfSeconds seconds, and on top of them it will show
// a countdown from countFrom back to 1, where each number will
// appear on the screen for (numOfSeconds / countFrom) secods, before
// it is replaced with the next one.

/**
 * The type Countdown animation.
 */
public class CountdownAnimation implements Animation {

    private int numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private Counter counter;

    /**
     * Instantiates a new Countdown animation.
     *
     * @param numOfSeconds the num of seconds
     * @param countFrom    the count from
     * @param gameScreen   the game screen
     */
    public CountdownAnimation(int numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.counter = new Counter(countFrom);
    }


    /**
     * do one frame.
     *
     * @param d the d
     */
    public void doOneFrame(DrawSurface d) {
        Sleeper sleeper = new Sleeper();
        this.gameScreen.drawAllOn(d);
        d.setColor(new Color(53, 99, 225));
        d.drawText(d.getWidth() / 2 - 55, d.getHeight() / 2 + 10, Integer.toString(this.counter.getValue()), 200);
        if (this.counter.getValue() != this.countFrom) {
            sleeper.sleepFor((1000 * numOfSeconds) / countFrom);
        }
        this.counter.decrease(1);
    }

    /**
     * return if should stop.
     *
     * @return if should stop
     */
    public boolean shouldStop() {
        return (this.counter.getValue() < 0);
    }
}
