package assignment3;

import edu.princeton.cs.introcs.StdDraw;

import java.util.Comparator;

/**
 * Immutable data type that represents a point in the plane
 *
 * @author Alex Ilyenko
 * @see java.lang.Comparable
 */
public class Point implements Comparable<Point> {
    /**
     * x coordinate of the {@code Point}
     */
    private final int x;
    /**
     * y coordinate of the {@code Point}
     */
    private final int y;
    /**
     * Custom comparator for comparing {@code Point}s by their slopes to each other
     *
     * @see #slopeTo(Point)
     * @see java.util.Comparator
     */
    public final Comparator<Point> SLOPE_ORDER = (o1, o2) -> {
        double slope1 = slopeTo(o1);
        double slope2 = slopeTo(o2);
        if (slope1 == slope2) return 0;
        if (slope1 < slope2) return -1;
        return 1;
    };

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws the current {@code Point}
     *
     * @see edu.princeton.cs.introcs.StdDraw#point(double, double)
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment from this {@code Point} to that {@code Point}
     *
     * @param that the second point for line segment{@code Point}
     * @see edu.princeton.cs.introcs.StdDraw#line(double, double, double, double)
     */
    public void drawTo(Point that) {
        StdDraw.line(x, y, that.x, that.y);
    }

    /**
     * String representation of the {@code Point}
     *
     * @return "(x, y)", where x - abscissa of the {@code Point} and y - its ordinate
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Compares points by their y-coordinates, breaking ties by their x-coordinates.
     * Formally, the invoking point (x0, y0) is less than the argument point (x1, y1) if
     * and only if either y0 < y1 or if y0 = y1 and x0 < x1
     *
     * @param that the point to compare with
     * @return 0 if points are lexicographically equal, -1 if this point is lexicographically
     * smaller than that one and 1 if opposite
     */
    @Override
    public int compareTo(Point that) {
        if (y == that.y && x == that.x) return 0;
        if (y < that.y || (y == that.y && x < that.x)) return -1;
        return 1;
    }

    /**
     * Returns the slope between the invoking point (x0, y0) and the argument point (x1, y1),
     * which is given by the formula (y1 − y0) / (x1 − x0).
     *
     * @param that the point to slope with
     * @return positive zero if slope belongs to horizontal line segment, {@link java.lang.Double#POSITIVE_INFINITY}
     * if slope belongs to vertical line segment, {@link java.lang.Double#NEGATIVE_INFINITY} if points are equals and
     * {@code double} value of the slope between two points in general case
     */
    public double slopeTo(Point that) {
        if (x == that.x) {
            if (y == that.y) return Double.NEGATIVE_INFINITY;
            return Double.POSITIVE_INFINITY;
        }
        if (y == that.y) return 0.0;
        return (double) (that.y - y) / (that.x - x);
    }


}
