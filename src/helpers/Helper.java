package helpers;

import geometry.Point;

/**
 * The type helpers.Helper.
 */
public class Helper {

    /**
     * Calc upper right point.
     *
     * @param upperLeft the upper left
     * @param width     the width
     * @param height    the height
     * @return the point
     */
    public static Point calcUpperRight(Point upperLeft, double width, double height) {
        return new Point(upperLeft.getX() + width, upperLeft.getY());
    }

    /**
     * Calc down left point.
     *
     * @param upperLeft the upper left
     * @param width     the width
     * @param height    the height
     * @return the point
     */
    public static Point calcDownLeft(Point upperLeft, double width, double height) {
        return new Point(upperLeft.getX(), upperLeft.getY() + height);
    }

    /**
     * Calc down right point.
     *
     * @param upperLeft the upper left
     * @param width     the width
     * @param height    the height
     * @return the point
     */
    public static Point calcDownRight(Point upperLeft, double width, double height) {
        return new Point(upperLeft.getX() + width, upperLeft.getY() + height);
    }
}
