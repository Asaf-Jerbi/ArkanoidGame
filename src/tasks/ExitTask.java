package tasks;

import game.animations.AnimationRunner;
import interfaces.Task;


/**
 * a ExitTask class.
 */
public class ExitTask implements Task<Void> {
    private AnimationRunner runner;

    /**
     * ExitTask.
     *
     * @param runner - the AnimationRunner.
     */
    public ExitTask(AnimationRunner runner) {
        this.runner = runner;
    }

    /**
     * run.
     *
     * @return null.
     */
    public Void run() {
        this.runner.getGui().close();
        return null;
    }
}


