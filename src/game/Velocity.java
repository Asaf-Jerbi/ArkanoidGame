package game;

import geometry.Point;

/**
 * game.Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {

    //fields
    private double dx;
    private double dy;

    /**
     * Instantiates a new game.Velocity.
     *
     * @param dx the dx
     * @param dy the dy
     */
// constructor
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Gets dx.
     *
     * @return the dx
     */
//access methods
    public double getDx() {
        return this.dx;
    }

    /**
     * Gets dy.
     *
     * @return the dy
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Gets step getSize.
     *
     * @return the step getSize
     */
    public double getStepSize() {
        return Math.sqrt(Math.pow(this.dx, 2) + Math.pow(this.dy, 2));
    }

    //methods

    /**
     * Apply to point point.
     *
     * @param p the p
     * @return the point
     */
// Take a point with position (x,y) and return a new point
    // with position (x+dx, y+dy)
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**
     * From angle and speed velocity.
     *
     * @param angleDegrees the angle degrees
     * @param speed        the speed
     * @return the velocity
     */
    public static Velocity fromAngleAndSpeed(double angleDegrees, double speed) {


        // convert degrees to radians
        double angleRadians = Math.toRadians(angleDegrees);

        // calculating dx, dy for velocity by using sin() and cos()
        double dx = Math.sin(angleRadians) * speed;
        double dy = Math.cos(angleRadians) * speed;

        return new Velocity(dx, dy);

    }

    /**
     * From angle and speed patch velocity.
     *
     * @param angleDegrees the angle degrees
     * @param speed        the speed
     * @return the velocity
     */
    public static Velocity fromAngleAndSpeedPatch(double angleDegrees, double speed) {


        // convert degrees to radians
        double angleRadians = Math.toRadians(angleDegrees);

        // calculating dx, dy for velocity by using sin() and cos()
        double dx = -Math.sin(angleRadians) * speed;
        double dy = Math.cos(angleRadians) * speed;

        return new Velocity(dx, dy);

    }
}