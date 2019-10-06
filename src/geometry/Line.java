package geometry;

import java.util.List;

/**
 * Represents a geometry.Line.
 *
 */
public class Line {

    //Fields
    private Point start, end;
    private double slope = Double.POSITIVE_INFINITY;
    private boolean isVertical = false;

    //===================================================================
    //Constructors
    //===================================================================

    /**
     * Instantiates a new geometry.Line.
     *
     * @param start the start point
     * @param end   the end point
     */
    public Line(Point start, Point end) {
        this.start = new Point(start.getX(), start.getY());
        this.end = new Point(end.getX(), end.getY());

        //if line is not vertical, calculate the slope
        if (this.start.getX() - this.end.getX() != 0) {
            this.slope = ((this.end.getY() - this.start.getY())
                    / (this.end.getX() - this.start.getX()));
        } else {
            //the slope does not exist
            this.isVertical = true;
        }
    }

    /**
     * Instantiates a new geometry.Line.
     *
     * @param startX x coordinate of start point
     * @param startY y coordinate of start point
     * @param endX   x coordinate of end point
     * @param endY   y coordinate of end point
     */
    public Line(double startX, double startY, double endX, double endY) {
        this(new Point(startX, startY), new Point(endX, endY));
    }


    //===================================================================
    //Getters
    //===================================================================

    /**
     * Returns the length of the line.
     *
     * @return the length
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Returns the middle point on the line.
     *
     * @return the middle point
     */
    public Point middle() {
        //calculates the middle point of the line
        double middleX = (this.start.getX() + this.end.getX()) / 2.0;
        double middleY = (this.start.getY() + this.end.getY()) / 2.0;

        //returns the middle point value
        return new Point(middleX, middleY);
    }

    /**
     * Returns the start point of the line.
     *
     * @return the start point
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the end point of the line.
     *
     * @return the end point
     */
    public Point end() {
        return this.end;
    }

    /**
     * Return right border.
     *
     * @return the right border
     */
    public double getRightBorder() {
        return Math.max(this.start.getX(), this.end.getX());
    }

    /**
     * Return left border.
     *
     * @return the left border
     */
    public double getLeftBorder() {
        return Math.min(this.start.getX(), this.end.getX());
    }

    /**
     * Return up border.
     *
     * @return the up border
     */
    public double getUpBorder() {
        return Math.max(this.start.getY(), this.end.getY());
    }

    /**
     * Return down border.
     *
     * @return the down border
     */
    public double getDownBorder() {
        return Math.min(this.start.getY(), this.end.getY());
    }

    /**
     * Returns true if lines intersect, false otherwise.
     *
     * @param other the other line
     * @return boolean, true - if intersect. false - otherwise
     */
    public boolean isIntersecting(Line other) {
        return (intersectionWith(other) != null);
    }

    //===================================================================
    //General methods
    //===================================================================

    /**
     * Returns the intersection point of two lines.
     * (if lines don't intersect - returns null)
     *
     * @param other the other line
     * @return the point or null
     */
    public Point intersectionWith(Line other) {

        //intersection point values
        double intersectionX = 0, intersectionY = 0;
        //variables
        double m1 = this.slope, m2 = other.slope,
                x1 = this.start.getX(), y1 = this.start.getY(),
                x2 = other.start.getX(), y2 = other.start.getY();

        //case 1: identical slopes
        if (m1 == m2) {
            //start point of other line is on this line
            if (this.isonLineSegment(other.start)) {
                return other.start;
            }
            //end point of other line is on this line
            if (this.isonLineSegment(other.end)) {
                return other.end;
            }

            //case 2: different slopes
        } else if (m1 != m2) {

            //case 2.1: none of lines is vertical (must be intersected)
            if ((!this.isVertical) && (!other.isVertical)) {
                //calc intersection point values (the '+ 0' is for changing the possible result of -0.0 to be 0.0):
                intersectionX = (((m1 * x1) - y1 - (m2 * x2)) + y2) / (m1 - m2) + 0;
                intersectionY = ((m1 * (intersectionX - x1)) + y1);
            }

            //case 2.2: current line is vertical and other is not:
            if (this.isVertical && !other.isVertical) {
                //calc intersection point values:
                intersectionX = this.start.getX();
                intersectionY = (m2 * (intersectionX - x2) + y2);
            }

            //case 2.3: other line is vertical and current is not:
            if (other.isVertical && !this.isVertical) {
                //calc intersection point values:
                intersectionX = other.start.getX();
                intersectionY = (m1 * (intersectionX - x1) + y1);
            }

            //create the intersection point:
            Point intersectionP = new Point(intersectionX, intersectionY);

            //check whether it's on both lines:
            if (this.isonLineSegment(intersectionP) && (other.isonLineSegment(intersectionP))) {
                return intersectionP;
            }
        }

        //if the intersection point is on the lines but not on the sections:
        return null;
    }

    /**
     * Returns whether the lines are equal.
     *
     * @param other the other line
     * @return boolean if equal or not
     */
    public boolean equals(Line other) {
        return (this.start == other.start && this.end == other.end)
                || (this.start == other.end && this.end == other.start);
    }


    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the
     * start of the line.
     *
     * @param rectangle the rectangle
     * @return the point
     */
    public Point closestIntersectionToStartOfLine(Rectangle rectangle) {

        //get intersection points list:
        List<Point> intersectionPoints = rectangle.intersectionPoints(this);

        if (intersectionPoints != null) {

            //search for the point on the rectangle with the smallest distance to the line:
            //(for now, the distance to the first ball will be the smallest distance, unless we find another one)
            double smallestDistance = intersectionPoints.get(0).distance(this.start);
            Point closetPoint = intersectionPoints.get(0);

            //find the real smallest distance:
            for (Point i : intersectionPoints) {
                if (i.distance(this.start) < smallestDistance) {
                    smallestDistance = i.distance(this.start);
                    closetPoint = i;
                }
            }
            return closetPoint;
        } else {
            return null;
        }
    }

    /**
     * Checking whether point is on line (with small allowed deviation).
     *
     * @param point the point
     * @return the boolean
     */
    public boolean isOnInfiniteLine(Point point) {
        final double allowedDeviation = 0.000005;
        double y, m = this.slope, x1 = this.start.getX(), y1 = this.start.getY();

        //case 1: line is vertical, check x values is enough
        if (this.isVertical) {
            if (this.start.getX() == point.getX()) {
                return true;
            }
        }

        //case 2: geometry.Line is not vertical, calculate the value of y:
        y = (m * (point.getX() - x1) + y1);

        //check whether it's the y of the point (with some allowed deviation).
        return ((Math.abs(y - point.getY()) < allowedDeviation) || (Math.abs(y - point.getY()) > allowedDeviation));
    }


    /**
     * Return whether point is in the line segment limits (between start and end points).
     * (but not necessarily on line itself)
     *
     * @param point the point
     * @return the boolean
     */
    public boolean isonLineSegment(Point point) {
        //return whether the point is on line AND on the line segment (between start and end points)
        return (Math.abs(this.start.distance(this.end)
                - point.distance(this.start)
                - point.distance(this.end)) <= 0.00000001);

//                && point.getX() >= getLeftBorder() && point.getX() <= getRightBorder()
//                && point.getY() <= getUpBorder() && point.getY() >= getDownBorder());
    }

}