package game.animations;

import biuoop.DrawSurface;
import helpers.Counter;

import java.awt.Color;

/**
 * The type Game over screen.
 */
public class GameOverScreen implements Animation {

    private Counter score;

    /**
     * Instantiates a new Game over screen.
     *
     * @param score the score
     */
    public GameOverScreen(Counter score) {
        this.score = score;
    }

    /**
     * do one frame.
     *
     * @param d the d
     */
    public void doOneFrame(DrawSurface d) {

        //String message = new String("Game Over! Your score is " + this.score.getValue());

        d.setColor(new Color(24, 41, 95));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

        //3D effect
        for (int i = 0; i < 5; i++) {
            d.setColor(new Color(106, 106, 106));
            d.drawText(140 + i, 200 - i, "Game Over! Your score is " + this.score.getValue(), 40);
        }

        //3D effect
        for (int i = 0; i < 8; i++) {
            d.setColor(new Color(115, 115, 115));
            d.drawText(232 + i, 250 - i, "press space to continue", 30);
        }

        //the text above (white color)
        d.setColor(new Color(255, 255, 255));
        d.drawText(145, 195, "Game Over! Your score is " + this.score.getValue(), 40);

        d.setColor(new Color(255, 255, 255));
        d.drawText(240, 242, "press space to continue", 30);
    }

    /**
     * should stop?.
     *
     * @return bool
     */
    public boolean shouldStop() {
        return false;
    }
}
