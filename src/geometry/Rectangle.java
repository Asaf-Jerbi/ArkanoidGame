package geometry;

import helpers.Helper;
import java.util.ArrayList;
import java.util.List;

/**
 * The type geometry.Rectangle.
 *
 */
public class Rectangle {

    //Fields
    private Point upperLeft, upperRight, downLeft, downRight;
    private double width, height;
    private Line leftSide, upSide, rightSide, bottomSide;


    //Constructors

    /**
     * Create a new rectangle with location and width/height.
     *
     * @param upperLeft the upper left point
     * @param width     the width
     * @param height    the height
     */
    public Rectangle(Point upperLeft, double width, double height) {

        //sizes
        this.width = width;
        this.height = height;

        //Points
        this.upperLeft = new Point(upperLeft.getX(), upperLeft.getY());
        this.upperRight = Helper.calcUpperRight(upperLeft, width, height);
        this.downLeft = Helper.calcDownLeft(upperLeft, width, height);
        this.downRight = Helper.calcDownRight(upperLeft, width, height);

        //sides
        this.leftSide = new Line(downLeft, upperLeft);
        this.bottomSide = new Line(downLeft, downRight);
        this.rightSide = new Line(downRight, upperRight);
        this.upSide = new Line(upperLeft, upperRight);
    }


    //General

    /**
     * Return a (possibly empty) List of intersection points with the specified line.
     *
     * @param line the line to check intersections with
     * @return list of intersection points
     */
    public List<Point> intersectionPoints(Line line) {

        List<Point> intersectionPoints = new ArrayList<>();

        //check intersection with each side of the geometry.Rectangle

        //left side
        if (this.leftSide.isIntersecting(line)) {
            intersectionPoints.add(this.leftSide.intersectionWith(line));
        }
        //bottom side
        if (this.bottomSide.isIntersecting(line)) {
            intersectionPoints.add(this.bottomSide.intersectionWith(line));
        }
        //right side
        if (this.rightSide.isIntersecting(line)) {
            intersectionPoints.add(this.rightSide.intersectionWith(line));
        }
        //up side
        if (this.upSide.isIntersecting(line)) {
            intersectionPoints.add(this.upSide.intersectionWith(line));
        }

        //return intersection points (if exist)
        if (!intersectionPoints.isEmpty()) {
            return intersectionPoints;
        } else {
            return null;
        }
    }


    //Getters

    /**
     * Gets width.
     *
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets upper left.
     *
     * @return the upper left point
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * Gets upper right.
     *
     * @return the upper right
     */
    public Point getUpperRight() {
        return upperRight;
    }

    //Setters

    /**
     * Sets new upper left.
     *
     * @param newUpperLeft the new upper left
     */
    /*public void setNewUpperLeft(geometry.Point newUpperLeft) {
        this.upperLeft = newUpperLeft;
    }*/

    /**
     * Gets down left.
     *
     * @return the down left
     */
    public Point getDownLeft() {
        return downLeft;
    }

    /**
     * Gets down right.
     *
     * @return the down right
     */
    public Point getDownRight() {
        return downRight;
    }

    /**
     * Gets left side.
     *
     * @return the left side
     */
    public Line getLeftSide() {
        return leftSide;
    }

    /**
     * Gets up side.
     *
     * @return the up side
     */
    public Line getUpSide() {
        return upSide;
    }

    /**
     * Gets right side.
     *
     * @return the right side
     */
    public Line getRightSide() {
        return rightSide;
    }

    /**
     * Gets bottom side.
     *
     * @return the bottom side
     */
    public Line getBottomSide() {
        return bottomSide;
    }




}