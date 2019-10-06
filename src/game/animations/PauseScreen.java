package game.animations;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The type Pause screen.
 */
public class PauseScreen implements Animation {

    /**
     * Instantiates a new Pause screen.
     */
    public PauseScreen() {
    }

    /**
     * do one frame.
     * @param d the d
     */
    public void doOneFrame(DrawSurface d) {
        d.setColor(new Color(24, 41, 95));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

        //3D effect
        for (int i = 0; i < 5; i++) {
            d.setColor(new Color(105, 97, 93));
            d.drawText(225 + i, 200 - i, "Paused", 100);
        }

        //3D effect
        for (int i = 0; i < 8; i++) {
            d.setColor(new Color(94, 94, 94));
            d.drawText(232 + i, 250 - i, "press space to continue", 33);
        }

        //the text above (white color)
        d.setColor(new Color(255, 0, 52));
        d.drawText(230, 195, "Paused", 100);

        d.setColor(new Color(255, 255, 255));
        d.drawText(240, 242, "press space to continue", 33);
    }

    /**
     * should stop.
     * @return bool
     */
    public boolean shouldStop() {
        return false;
    }


}

