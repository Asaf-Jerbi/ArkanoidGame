package game;

import sprites.Sprite;
import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * The type sprites.Sprite collection.
 */
public class SpriteCollection {

    //fields:
    private List<Sprite> sprites;

    /**
     * Instantiates a new sprites.Sprite collection.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }


    //methods:

    /**
     * Add sprite.
     *
     * @param s the sprite
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }


    /**
     * Remove sprite.
     *
     * @param s the s
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * Notify all time passed.
     */
    public void notifyAllTimePassed() {

        //copying the lists here is necessary to prevent "ConcurrentModificationException"
        //explained here: https://stackoverflow.com/questions/18448671/how-to-avoid-
        // concurrentmodificationexception-while-removing-elements-from-arr
        List<Sprite> currentSprites = new ArrayList<>(this.sprites); //DO NOT change this line!!!!
        for (Sprite i : currentSprites) {
            i.timePassed();
        }
    }

    /**
     * Draw all on surface.
     *
     * @param d the d
     */
    public void drawAllOn(DrawSurface d) {

        //copying the lists here is necessary to prevent "ConcurrentModificationException"
        //explained here: https://stackoverflow.com/questions/18448671/how-to-avoid-
        // concurrentmodificationexception-while-removing-elements-from-arr
        List<Sprite> currentSprites = new ArrayList<>(this.sprites); //DO NOT change this line!!!!
        for (Sprite i : currentSprites) {
            i.drawOn(d);
        }
    }
}

