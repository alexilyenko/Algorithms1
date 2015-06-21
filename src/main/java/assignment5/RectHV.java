package assignment5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.introcs.StdDraw;

import java.util.Objects;

/**
 * An immutable data type representing 2D axis-aligned rectangle
 *
 * @author Alex Ilyenko
 */
public class RectHV {
    /**
     * Minimum abscissa point of the rectangle
     */
    private final double xMin;
    /**
     * Maximum abscissa point of the rectangle
     */
    private final double xMax;
    /**
     * Minimum ordinate point of the rectangle
     */
    private final double yMin;
    /**
     * Maximum ordinate point of the rectangle
     */
    private final double yMax;

    /**
     * Constructs the 2D axis-aligned rectangle
     *
     * @param xmin minimum X point
     * @param ymin minimum Y point
     * @param xmax maximum X point
     * @param ymax maximum Y point
     * @throws IllegalArgumentException if either xMin > xMax or yMin > yMax
     */
    public RectHV(double xmin, double ymin, double xmax, double ymax) {
        if (xmin > xmax || ymin > ymax) {
            throw new IllegalArgumentException("Min point should not be more than max point!\n" +
                    "xMin: " + xmin + ", xMax: " + xmax + "\n" +
                    "yMin: " + ymin + ", yMax: " + ymax);
        }

        this.xMax = xmax;
        this.xMin = xmin;
        this.yMax = ymax;
        this.yMin = ymin;
    }

    /**
     * Minimum X getter
     *
     * @return xMin
     */
    public double xmin() {
        return xMin;
    }                           // minimum x-coordinate of rectangle

    /**
     * Minimum Y getter
     *
     * @return yMin
     */
    public double ymin() {
        return yMin;
    }                 // minimum y-coordinate of rectangle

    /**
     * Maximum X getter
     *
     * @return xMax
     */
    public double xmax() {
        return xMax;
    }                            // maximum x-coordinate of rectangle

    /**
     * Maximum Y getter
     *
     * @return yMax
     */
    public double ymax() {
        return yMax;
    }                            // maximum y-coordinate of rectangle

    /**
     * Shows if rectangle contains given point either inside or on boundaries
     *
     * @param p {@code Point} representing given point
     * @return {@code true} if rectangle contains given point, otherwise {@code false}
     * @see Point2D
     */
    public boolean contains(Point2D p) {
        return p.x() <= xMax && p.x() >= xMin
                && p.y() <= yMax && p.y() >= yMin;
    }

    /**
     * Shows if this rectangle intersects given rectangle at one or more points
     *
     * @param that {@code RectHV} representing given rectangle
     * @return {@code true} if rectangles intersect, otherwise {@code false}
     */
    public boolean intersects(RectHV that) {
        return this.xMax >= that.xMin && this.yMax >= that.yMin
                && that.xMax >= this.xMax && that.yMax >= this.yMin;
    }

    /**
     * Calculates Euclidean distance from given point to the closest point in rectangle
     *
     * @param p {@code Point} representing given point
     * @return return distance value
     * @see #distanceSquaredTo(Point2D)
     */
    public double distanceTo(Point2D p) {
        return Math.sqrt(distanceSquaredTo(p));
    }

    /**
     * Calculates quadratic Euclidean distance from given point to the closest point in rectangle
     *
     * @param p {@code Point} representing given point
     * @return quadratic distance value
     */
    public double distanceSquaredTo(Point2D p) {
        double dX = 0;
        double dY = 0;
        if (p.x() < xMin) {
            dX = p.x() - xMin;
        } else if (p.x() > xMax) {
            dX = p.x() - xMax;
        }
        if (p.y() < yMin) {
            dY = p.y() - yMin;
        } else if (p.y() > yMax) {
            dY = p.y() - yMax;
        }
        return dX * dX + dY * dY;
    }


    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        final RectHV thatRect = (RectHV) that;
        return xMax == thatRect.xMax && xMin == thatRect.xMin
                && yMax == thatRect.yMax && yMin == thatRect.yMin;
    }              // does this rectangle equal that?

    @Override
    public int hashCode() {
        return Objects.hash(xMax, xMin, yMax, yMin);
    }

    /**
     * Draws the rectangle
     *
     * @see StdDraw#line(double, double, double, double)
     */
    public void draw() {
        StdDraw.line(xMin, yMin, xMax, yMin);
        StdDraw.line(xMin, yMax, xMax, yMax);
        StdDraw.line(xMax, yMax, xMax, yMin);
        StdDraw.line(xMin, yMax, xMin, yMin);
    }

    @Override
    public String toString() {
        return "Rectangle: {xMax: " + xMax +
                ", yMax: " + yMax +
                ", xMin: " + xMin +
                ", yMin: " + yMin + "}";
    }

}
