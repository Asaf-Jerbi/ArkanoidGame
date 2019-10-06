package sprites;

import biuoop.DrawSurface;
import game.Velocity;
import geometry.Point;
import geometry.Rectangle;

/**
 * The interface sprites.Collidable - represents collidable objects.
 */
public interface Collidable {

    /**
     * Return the "collision shape" of the object.
     *
     * @return the collision rectangle
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     *
     * @param hitter          the ball
     * @param collisionPoint  the collision point
     * @param currentVelocity the current velocity
     * @return the velocity
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

    /**
     * Draw on.
     *
     * @param surface the surface
     */
    void drawOn(DrawSurface surface);
}
