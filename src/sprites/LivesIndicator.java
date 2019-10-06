package sprites;

import game.GameLevel;
import helpers.Counter;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The type Lives indicator.
 */
public class LivesIndicator implements Sprite {

    private Counter remainingLives;

    /**
     * Instantiates a new Lives indicator.
     *
     * @param remainingLives the remaining lives
     */
    public LivesIndicator(Counter remainingLives) {
        this.remainingLives = remainingLives;
    }


    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(75, 15, "L i v e s : " + this.remainingLives.getValue(), 15);
        d.drawText(74, 15, "L i v e s : " + this.remainingLives.getValue(), 15);
        d.drawText(76, 15, "L i v e s : " + this.remainingLives.getValue(), 15);
        d.drawText(75, 14, "L i v e s : " + this.remainingLives.getValue(), 15);
        d.drawText(75, 16, "L i v e s : " + this.remainingLives.getValue(), 15);
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
