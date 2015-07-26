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
    private final double xmin, ymin; // minimum x- and y-coordinates  
    private final double xmax, ymax; // maximum x- and y-coordinates  

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
        if (xmax < xmin || ymax < ymin) {
            throw new IllegalArgumentException("Min point should not be more than max point!\n" +
                    "xMin: " + xmin + ", xMax: " + xmax + "\n" +
                    "yMin: " + ymin + ", yMax: " + ymax);
        }
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }

    public double xmin() {
        return xmin;
    }

    public double ymin() {
        return ymin;
    }

    public double xmax() {
        return xmax;
    }

    public double ymax() {
        return ymax;
    }

    public double width() {
        return xmax - xmin;
    }

    public double height() {
        return ymax - ymin;
    }

    /**
     * Shows if this rectangle intersects given rectangle at one or more points
     *
     * @param that {@code RectHV} representing given rectangle
     * @return {@code true} if rectangles intersect, otherwise {@code false}
     */
    public boolean intersects(RectHV that) {
        return this.xmax >= that.xmin && this.ymax >= that.ymin
                && that.xmax >= this.xmin && that.ymax >= this.ymin;
    }

    /**
     * Draws the rectangle
     *
     * @see StdDraw#line(double, double, double, double)
     */
    public void draw() {
        StdDraw.line(xmin, ymin, xmax, ymin);
        StdDraw.line(xmax, ymin, xmax, ymax);
        StdDraw.line(xmax, ymax, xmin, ymax);
        StdDraw.line(xmin, ymax, xmin, ymin);
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
        double dx = 0.0, dy = 0.0;
        if (p.x() < xmin) {
            dx = p.x() - xmin;
        } else if (p.x() > xmax) {
            dx = p.x() - xmax;
        }
        if (p.y() < ymin) {
            dy = p.y() - ymin;
        } else if (p.y() > ymax) {
            dy = p.y() - ymax;
        }
        return dx * dx + dy * dy;
    }

    /**
     * Shows if rectangle contains given point either inside or on boundaries
     *
     * @param p {@code Point} representing given point
     * @return {@code true} if rectangle contains given point, otherwise {@code false}
     * @see Point2D
     */
    public boolean contains(Point2D p) {
        return (p.x() >= xmin) && (p.x() <= xmax) && (p.y() >= ymin)
                && (p.y() <= ymax);
    }

    @Override
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        RectHV that = (RectHV) y;
        return this.xmin == that.xmin && this.ymin == that.ymin
                && this.xmax == that.xmax && this.ymax == that.ymax;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xmax, xmin, ymax, ymin);
    }

    @Override
    public String toString() {
        return "[" + xmin + ", " + xmax + "] x [" + ymin + ", " + ymax + "]";
    }
}  