package game.animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The type Key press stoppable animation.
 */
public class KeyPressStoppableAnimation implements Animation {

    private KeyboardSensor keyboardSensor;
    private String key;
    private Animation decorated;
    private boolean shouldStop;
    private boolean isStartOfAnimation;
    private boolean shouldIgnore;

    /**
     * Instantiates a new Key press stoppable animation.
     *
     * @param animation      the animation
     * @param keyboardSensor the keyboard sensor
     * @param key            the key
     */
    public KeyPressStoppableAnimation(Animation animation, KeyboardSensor keyboardSensor, String key) {
        this.keyboardSensor = keyboardSensor;
        this.key = key;
        this.decorated = animation;
        this.isStartOfAnimation = true;
        this.shouldIgnore = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {

        //check if there is a pressed key from prev animations:
        if (this.isStartOfAnimation) {
            this.shouldIgnore = this.keyboardSensor.isPressed(this.key);
            this.isStartOfAnimation = false;
        }

        //do one frame of decorated
        this.decorated.doOneFrame(d);

        //if shouldn't ignore the pressed key (meaning it wasn't pressed in prev animations
        //now it should get it. otherwise - ignore the press and wait for the next one (which
        //possible was made just now, but will wait to next round to be absorbed and it will
        //thanks to "startOfAnimation" which becomes false and "shouldIgnore" that becomes false too.
        if (this.keyboardSensor.isPressed(this.key)) {
            if (!this.shouldIgnore) {
                this.shouldStop = true;
            }
        } else {
            this.shouldIgnore = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.shouldStop;
    }

    /**
     * Reset.
     */
    public void reset() {
        this.shouldStop = false;
    }

}
