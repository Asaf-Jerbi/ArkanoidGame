package game.animations;

import biuoop.DrawSurface;
import helpers.Counter;

import java.awt.Color;

/**
 * The type You win screen.
 */
public class YouWinScreen implements Animation {

    private Counter score;

    /**
     * Instantiates a new You win screen.
     *
     * @param score the score
     */
    public YouWinScreen(Counter score) {
        this.score = score;
    }


    /**
     * do onr frame.
     * @param d the d
     */
    public void doOneFrame(DrawSurface d) {
        //String message = new String("You Win! Your score is " + this.score.getValue());
        d.setColor(new Color(24, 41, 95));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

        //3D effect
        for (int i = 0; i < 5; i++) {
            d.setColor(new Color(119, 119, 119));
            d.drawText(160 + i, 200 - i, "Winner! Your score is " + this.score.getValue(), 40);
        }

        //3D effect
        for (int i = 0; i < 8; i++) {
            d.setColor(new Color(119, 119, 119));
            d.drawText(232 + i, 250 - i, "press space to continue", 30);
        }

        //the text above (white color)
        d.setColor(new Color(255, 255, 255));
        d.drawText(165, 195, "Winner! Your score is " + this.score.getValue(), 40);

        d.setColor(new Color(255, 255, 255));
        d.drawText(240, 242, "press space to continue", 30);
    }

    /**
     * should stop.
     * @return bool
     */
    public boolean shouldStop() {
        return false;
    }

}
