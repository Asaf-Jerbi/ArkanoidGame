package game.backgrounds;

import biuoop.DrawSurface;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import interfaces.BackGround;
import sprites.Sprite;

import java.awt.Image;
import java.awt.Color;


/**
 * The type Image background.
 */
public class ImageBackground implements Sprite, BackGround {
    private Image image;
    private Rectangle rec = new Rectangle(new Point(0, 0), 800, 600);


    /**
     * Instantiates a new Image background.
     *
     * @param image the image
     */
    public ImageBackground(Image image) {
        this.image = image;
    }


    /**
     * draw on.
     *
     * @param d the d
     */
    public void drawOn(DrawSurface d) {
        int x = (int) this.rec.getUpperLeft().getX();
        int y = (int) this.rec.getUpperLeft().getY();
        d.drawImage(x, y, this.image);

    }

    /**
     * time passed.
     */
    public void timePassed() {
    }

    /**
     * set rec.
     *
     * @param rectangle the rectangle
     */
    public void setRec(Rectangle rectangle) {
        this.rec = rectangle;
    }

    /**
     * set stroke.
     *
     * @param color the color
     */
    public void setStroke(Color color) {
    }

    /**
     * add to game.
     *
     * @param gameLevel the gamelevel
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }
}
