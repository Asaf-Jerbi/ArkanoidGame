package sprites;

import biuoop.DrawSurface;
import game.GameLevel;

import java.awt.Color;

/**
 * The type Level indicator.
 */
public class LevelIndicator implements Sprite {

    private String levelName;

    /**
     * Instantiates a new Level indicator.
     *
     * @param levelName the level name
     */
    public LevelIndicator(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(549, 15, "L e v e l : " + this.levelName, 15);
        d.drawText(550, 15, "L e v e l : " + this.levelName, 15);
        d.drawText(550, 14, "L e v e l : " + this.levelName, 15);
        d.drawText(550, 15, "L e v e l : " + this.levelName, 15);
    }

    @Override
    public void timePassed() {

    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
