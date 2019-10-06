package sprites;

import game.GameLevel;
import helpers.Counter;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The type Score indicator.
 */
public class ScoreIndicator implements Sprite {

    private Counter gameScore;
    private int height;


    /**
     * Instantiates a new Score indicator.
     *
     * @param gameScore the game score
     */
    public ScoreIndicator(Counter gameScore) {
        this.gameScore = gameScore;
        this.height = 20;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    @Override
    public void drawOn(DrawSurface d) {

        d.drawRectangle(0, 0, 800, this.height);

        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, 800, this.height);

        d.setColor(Color.BLACK);
        d.drawText(360, 15, "S c o r e : " + this.gameScore.getValue(), 15);
        d.drawText(361, 15, "S c o r e : " + this.gameScore.getValue(), 15);
        d.drawText(359, 15, "S c o r e : " + this.gameScore.getValue(), 15);
        d.drawText(360, 14, "S c o r e : " + this.gameScore.getValue(), 15);
        d.drawText(360, 16, "S c o r e : " + this.gameScore.getValue(), 15);
    }

    @Override
    public void timePassed() {
    }

    /**
     * Add to game.
     *
     * @param g the g
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
