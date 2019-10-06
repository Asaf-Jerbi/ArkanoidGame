package game.backgrounds;

import biuoop.DrawSurface;
import game.GameLevel;
import geometry.Rectangle;
import interfaces.BackGround;
import sprites.Block;
import sprites.Sprite;

import java.awt.Color;


/**
 * The type Color background.
 */
public class ColorBackground implements Sprite, BackGround {
    private Color color = Color.cyan;
    private Color outlineColor = Color.black;
    private Block b = new Block(0, 0, 800, 600);

    @Override
    public void drawOn(DrawSurface d) {
        b.setColor(this.color);
        b.drawOn(d);
    }

    @Override
    public void timePassed() {
    }

    /**
     * Sets color.
     *
     * @param colour the colour
     */
    public void setColor(Color colour) {
        this.color = colour;
    }


    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * set rectangle.
     *
     * @param rectangle the rectangle
     */
    public void setRec(Rectangle rectangle) {
        this.b.setRectangle(rectangle);
    }


    @Override
    public void setStroke(Color colour) {
        this.outlineColor = colour;
    }


    @Override
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }
}
