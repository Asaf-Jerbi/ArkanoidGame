package geometry;

/**
 * represents a point.
 *
 */
public class Point {

    // Fields
    private double x;
    private double y;

    /**
     * Instantiates a new geometry.Point.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * calculates the distance to other point.
     *
     * @param other the other point
     * @return the distance (as double)
     */
    public double distance(Point other) {

        double x1 = this.x, y1 = this.y, x2 = other.x, y2 = other.y;

        //return distance between points
        return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
    }

    /**
     * checks if this point is equal to other point.
     *
     * @param other the other
     * @return bolean (true - if equal, false otherwise)
     */
    public boolean equals(Point other) {
        return (Math.abs(this.x - other.x) < 0.0005 && Math.abs(this.y - other.y) < 0.0005);
    }

    /**
     * returns the x value of this point.
     *
     * @return the x
     */
    public double getX() {
        return this.x;
    }


    /**
     * returns the y value of this point.
     *
     * @return the y
     */
    public double getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "geometry.Point{" + "x=" + x + ", y=" + y + '}';
    }
}