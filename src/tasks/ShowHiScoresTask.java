package tasks;

import game.animations.Animation;
import game.animations.AnimationRunner;
//import gameutils.AnimationRunner;
//import interfaces.Animation;
import interfaces.Task;

/**
 * a ShowHiScoresTask class.
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation highScoresAnimation;

    /**
     * ShowHiScoresTask - constructor.
     *
     * @param runner              the AnimationRunner.
     * @param highScoresAnimation - highScoresAnimation.
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
    }

    /**
     * run.
     *
     * @return null.
     */
    public Void run() {
        this.runner.run(this.highScoresAnimation);
        return null;
    }
}
