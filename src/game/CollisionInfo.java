package game;

import sprites.Collidable;
import geometry.Point;

/**
 * The type Collision info.
 */
public class CollisionInfo {

    //Fields
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * Instantiates a new Collision info.
     *
     * @param collisionPoint  the collision point
     * @param collisionObject the collision object
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * Gets the collidable object involved in the collision.
     *
     * @return the collision object
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }

    /**
     * Gets the point at which the collision occurs.
     *
     * @return the collision point
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

}
