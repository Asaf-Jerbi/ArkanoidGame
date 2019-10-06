package sprites;

import biuoop.DrawSurface;
import game.GameLevel;
import game.Velocity;
import geometry.Point;
import geometry.Rectangle;
import interfaces.BackGround;
import listeners.HitListener;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type sprites.Block.
 */
public class Block implements Collidable, Sprite, HitNotifier {

    //Fields
    private Rectangle rectangle;
    private Color color = Color.CYAN;
    private int hitPoint;
    private List<HitListener> hitListeners = null;
    private BackGround bg = null;
    private Map<Integer, BackGround> hitPointBg = new TreeMap<>();


    /**
     * Instantiates a new sprites.Block.
     *
     * @param upperLeft the upper left
     * @param width     the width
     * @param height    the height
     * @param color     the color
     * @param numOfHits the num of hits
     */
//Constructor
    public Block(Point upperLeft, double width, double height, Color color, int numOfHits) {
        this.rectangle = new Rectangle(upperLeft, width, height);
        this.color = color;
        this.hitPoint = numOfHits;
        this.hitListeners = new ArrayList<>();
    }

    /**
     * Instantiates a new sprites.Block.
     *
     * @param upperLeftX the upper left x
     * @param upperLeftY the upper left y
     * @param width      the width
     * @param height     the height
     * @param numOfHits  the num of hits
     */
    public Block(double upperLeftX, double upperLeftY, double width, double height, int numOfHits) {
        this.rectangle = new Rectangle(new Point(upperLeftX, upperLeftY), width, height);
        this.hitPoint = numOfHits;
        this.hitListeners = new ArrayList<>();
    }


    /**
     * Instantiates a new sprites.Block.
     *
     * @param upperLeftX the upper left x
     * @param upperLeftY the upper left y
     * @param width      the width
     * @param height     the height
     */
    public Block(double upperLeftX, double upperLeftY, double width, double height) {
        this(upperLeftX, upperLeftY, width, height, 0);
    }

    /**
     * Instantiates a new sprites.Block.
     *
     * @param upperLeft the upper left
     * @param width     the width
     * @param height    the height
     * @param color     the color
     */
    public Block(Point upperLeft, double width, double height, Color color) {
        this(upperLeft, width, height, color, 0);
    }


    /**
     * Sets rectangle.
     *
     * @param newRactangle the new ractangle
     */
//setters:
    public void setRectangle(Rectangle newRactangle) {
        this.rectangle = newRactangle;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * Sets hit point.
     *
     * @param hittPoint the hit point
     */
    public void setHittPoint(int hittPoint) {
        this.hitPoint = hittPoint;
    }


    /**
     * Gets hits number.
     *
     * @return the hits number
     */
//getters
    public int getHitPoints() {
        return hitPoint;
    }


    //general methods

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     *
     * @param hitter          the hitter
     * @param collisionPoint  the collision point
     * @param currentVelocity the current velocity
     * @return the velocity
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        Velocity newVelocity = currentVelocity;

        //case 0 - collision point is one of the corners of the rectangle
        if (collisionPoint.equals(this.rectangle.getUpperLeft())
                || collisionPoint.equals(this.rectangle.getUpperRight())
                || collisionPoint.equals(this.rectangle.getDownLeft())
                || collisionPoint.equals(this.rectangle.getDownRight())) {

            newVelocity = new Velocity(newVelocity.getDx() * (-1), newVelocity.getDy() * (-1));
            //case 1 - collision point is on the left side (reverse dx)
        } else if (this.rectangle.getLeftSide().isonLineSegment(collisionPoint)) {
            newVelocity = new Velocity(newVelocity.getDx() * (-1), newVelocity.getDy());
            //case 2 - collision point is on the up side (reverse dy)
        } else if (this.rectangle.getUpSide().isonLineSegment(collisionPoint)) {
            newVelocity = new Velocity(newVelocity.getDx(), newVelocity.getDy() * (-1));
            //case 3 - collision point is on the right side (reverse dx)
        } else if (this.rectangle.getRightSide().isonLineSegment(collisionPoint)) {
            newVelocity = new Velocity(newVelocity.getDx() * (-1), newVelocity.getDy());
            //case 4 - collision point is on the bottom side (reverse dy)
        } else if (this.rectangle.getBottomSide().isonLineSegment(collisionPoint)) {
            newVelocity = new Velocity(newVelocity.getDx(), newVelocity.getDy() * (-1));
        }

        //decrease hits:
        if (this.hitPoint > 0) {
            this.hitPoint--;
        }

        //notify hit:
        this.notifyHit(hitter);

        //return new velocity:
        return newVelocity;

    }

    @Override
    public void drawOn(DrawSurface surface) {

        if (this.hitPointBg.containsKey(this.hitPoint)) {
            this.bg = this.hitPointBg.get(this.hitPoint);
            this.hitPointBg.get(this.hitPoint).drawOn(surface);
        } else if (this.bg != null) {
            this.bg.drawOn(surface);
        } else { //default
            defaultDrawOn(surface); //draw the frame blocks.
        }
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
        g.getSprites().addSprite(this);
        g.getGameEnvironment().addCollidable(this);
    }

    /**
     * Remove from gameLevel.
     *
     * @param gameLevel the gameLevel
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    /**
     * Notify hit.
     *
     * @param hitter the hitter
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        if (this.hitListeners != null) {
            List<HitListener> listeners = new ArrayList<>(this.hitListeners);
            // Notify all listeners about a hit event:
            for (HitListener hl : listeners) {
                hl.hitEvent(this, hitter);
            }
        }
    }


    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }


    /**
     * Add hp background.
     *
     * @param hPbG the h pb g
     */
    public void addBackground(Map<Integer, BackGround> hPbG) {
        this.hitPointBg = hPbG;
    }

    /**
     * Default draw on.
     *
     * @param surface the surface
     */
    private void defaultDrawOn(DrawSurface surface) {
        surface.setColor(this.color);
//        surface.setColor(Color.CYAN);
        surface.fillRectangle((int) this.rectangle.getUpperLeft().getX(),
                (int) this.rectangle.getUpperLeft().getY(),
                (int) this.rectangle.getWidth(),
                (int) this.rectangle.getHeight());

        surface.setColor(Color.BLACK);
        surface.drawRectangle((int) this.rectangle.getUpperLeft().getX(),
                (int) this.rectangle.getUpperLeft().getY(),
                (int) this.rectangle.getWidth(),
                (int) this.rectangle.getHeight());

        //write on each block the number of hits:

        int middleOfBlock = (int) this.getCollisionRectangle().getWidth() / 2;
        int middleOfHeight = (int) this.getCollisionRectangle().getHeight() / 2;
        if (this.hitPoint != 0) {
            String numberOfHits = new String("" + this.hitPoint);
            surface.drawText((int) this.rectangle.getUpperLeft().getX() + middleOfBlock,
                    (int) this.rectangle.getUpperLeft().getY() + middleOfHeight, numberOfHits, 9);
        }
//         else {
//            //todo: I removed writing X on block. return if needed.
////            surface.drawText((int) this.rectangle.getUpperLeft().getX() + middleOfBlock,
////                    (int) this.rectangle.getUpperLeft().getY() + middleOfHeight, "x", 9);
//        }

        //FOR TESTING USE:
//        String stam = new String("" + this);
//        String stam2 = new String(stam.replace("sprites.Block@", ""));
//        String stam3 = new String(this.rectangle.getUpperLeft().toString());
//        String stam4 = new String(stam3.replace("geometry.Point", ""));
//        stam4 = stam4.replace(".0", "");
//        stam4 = stam4.replace("{", "");
//        stam4 = stam4.replace("}", "");
//
//        surface.drawText((int) this.rectangle.getUpperLeft().getX() + 4,
//        (int) this.rectangle.getUpperLeft().getY() + 12,
//                stam4, 9);
    }

    /**
     * Sets color.
     *
     * @param colour the color
     */
    public void setColor(Color colour) {
        this.color = colour;
    }

}



