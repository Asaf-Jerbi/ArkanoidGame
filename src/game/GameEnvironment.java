package game;

import sprites.Collidable;
import geometry.Line;
import geometry.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * The type game.GameLevel environment.
 */
public class GameEnvironment {


    //Fields
    private List<Collidable> collidables;


    //Constructors
    /**
     * Instantiates a new game.GameLevel environment.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }


    //Getters
    /**
     * Gets collidables.
     *
     * @return the collidables
     */
    public List<Collidable> getCollidables() {
        return collidables;
    }


    //General methods
    /**
     * add a given collidable to the environment.
     *
     * @param newCollidable the new collidable
     */
    public void addCollidable(Collidable newCollidable) {
        this.collidables.add(newCollidable);
    }


    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     *
     * @param trajectory the trajectory
     * @return the collision info
     */
    public CollisionInfo getClosestCollision(Line trajectory) {

        //initializations:
        Point collisionPoint = new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        Collidable collisionObject = null;

        //find the closet collision point from all collidables objects available

        //copying the lists here is necessary to prevent "ConcurrentModificationException"
        //explained here: https://stackoverflow.com/questions/18448671/how-to-avoid-
        // concurrentmodificationexception-while-removing-elements-from-arr
        List<Collidable> currentCollidables = new ArrayList<>(this.collidables); //do not change this line
        for (Collidable collidable : currentCollidables) {

            //save the collision point with the collidable object
            Point intersectionWithCollidable =
                    trajectory.closestIntersectionToStartOfLine(collidable.getCollisionRectangle());


            //save the closest point as the collision point:
            if (intersectionWithCollidable != null && intersectionWithCollidable.distance(trajectory.start())
                    < collisionPoint.distance(trajectory.start())) {
                collisionPoint = intersectionWithCollidable;
                collisionObject = collidable;
            }
        }

        return new CollisionInfo(collisionPoint, collisionObject);
    }
}
