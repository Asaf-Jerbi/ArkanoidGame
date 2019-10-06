package sprites;

import biuoop.DrawSurface;
import game.CollisionInfo;
import game.GameLevel;
import game.GameEnvironment;
import game.Velocity;
import geometry.Line;
import geometry.Point;

import java.awt.Color;

/**
 * The type sprites.Ball.
 */
public class Ball implements Sprite {


    //Fields:
    private Point center;
    private int size;
    private Velocity velocity;
    private GameEnvironment gameEnvironment = null;
    private GameLevel gameLevel;


    // constructors

    /**
     * Instantiates a new sprites.Ball.
     *
     * @param center the center
     * @param r      the r
     */
    public Ball(Point center, int r) {
        this.center = new Point(center.getX(), center.getY());
        this.size = r;
        this.velocity = new Velocity(0, 0); //default velocity
    }


    /**
     * Instantiates a new sprites.Ball.
     *
     * @param centerX the center x
     * @param centerY the center y
     * @param r       the r
     */
    public Ball(int centerX, int centerY, int r) {
        this(new Point(centerX, centerY), r);
    }

    // Getters:

    /**
     * returns the x coordinate of the center of the ball.
     *
     * @return the x
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * returns the y coordinate of the center of the ball.
     *
     * @return the y
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Gets center.
     *
     * @return the center
     */
    public Point getCenter() {
        return center;
    }

    /**
     * returns the getSize (the radius) of the ball.
     *
     * @return the getSize
     */
    public int getSize() {
        return this.size;
    }

    /**
     * returns the color of the ball.
     *
     * @return the color
     */
//    public Color getColor() {
//        return this.color;
//    }

    /**
     * returns the velocity of the ball.
     *
     * @return the velocity
     */
    public Velocity getVelocity() {
        return this.velocity;
    }


    /**
     * Gets gameLevel environment.
     *
     * @return the gameLevel environment
     */
    public GameEnvironment getGameEnvironment() {
        return gameEnvironment;
    }

    //Setters:

    /**
     * Sets velocity by getting a velocity.
     *
     * @param v the velocity to set up
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Sets velocity by getting dx and dy.
     *
     * @param dx the dx of the velocity
     * @param dy the dy of the velocity
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }


    //General methods

    /**
     * draw the ball on the given DrawSurface.
     *
     * @param surface the surface
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(new Color(229, 255, 0));
        surface.fillCircle(this.getX(), this.getY(), this.size);
        surface.setColor(new Color(0, 0, 2));
        surface.drawCircle(this.getX(), this.getY(), this.size);
        surface.setColor(new Color(0, 0, 2));
        surface.setColor(new Color(245, 255, 0));
    }

    /**
     * Calc trajectory line.
     *
     * @return the line
     */
    private Line calcTrajectory() {
        double endX = this.center.getX() + this.velocity.getDx();
        double endY = this.center.getY() + this.velocity.getDy();

        if (this.velocity.getDx() > 0) {
            endX = endX + this.size;
        }

        if (this.velocity.getDy() > 0) {
            endY = endY + this.size;
        }

        if (this.velocity.getDx() < 0) {
            endX = endX - this.size;
        }

        if (this.velocity.getDy() < 0) {
            endY = endY - this.size;
        }

        return new Line(this.center, new Point(endX, endY));
    }

    /**
     * Move the ball one step according it's velocity value.
     */
    public void moveOneStep() {

        //Collision information stores the intersection point and the object:
        CollisionInfo collisionInfo;
        //creating the trajectory of the ball
        Line trajectory = calcTrajectory();

        //check if trajectory has intersection with any object on the screen:
        collisionInfo = this.gameEnvironment.getClosestCollision(trajectory);

        //locals:
        Point collisionP = collisionInfo.collisionPoint();
        Collidable collisionObject = collisionInfo.collisionObject();

        if (collisionObject != null) {
            this.setVelocity(collisionObject.hit(this, collisionP, this.velocity));
            this.center = this.velocity.applyToPoint(this.center);
        } else {
            this.center = this.velocity.applyToPoint(this.center);
        }
    }

    @Override
    public void timePassed() {
        moveOneStep();
    }

    /**
     * Add to gameLevel.
     *
     * @param g the g
     */
    public void addToGame(GameLevel g) {
        g.getSprites().addSprite(this);
        this.gameLevel = g;
        this.gameEnvironment = g.getGameEnvironment();
    }

    /**
     * Remove from gameLevel.
     *
     * @param g the g
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }

}