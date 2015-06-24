package assignment5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdRandom;

import java.awt.*;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Mutable data type that represents a set of points in the unit square. It uses a red-black binary search tree
 * (TreeSet implementation). Current implementation supports{@link #insert(Point2D)} and {@link #contains(Point2D)}
 * in time proportional to the logarithm of the number of points in the set in the worst case; also it supports
 * {@link #nearest(Point2D)} and {@link #range(RectHV)} in time proportional to the number of points in the set.
 *
 * @author Alex Ilyenko
 * @see Point2D
 * @see RectHV
 * @see TreeSet
 * @see <a href="https://en.wikipedia.org/wiki/Red%E2%80%93black_tree">Red-black tree structures on Wikipedia</a>
 */
public class PointSET {
    /**
     * Points in the unit square
     */
    private final Set<Point2D> points;

    /**
     * Constructs an empty set of points
     */
    public PointSET() {
        points = new TreeSet<>();
    }

    /**
     * Shows if the set of points empty
     *
     * @return <tt>true</tt> if this set contains no elements, otherwise - <tt>false</tt>
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /**
     * Indicates the number of points in the set
     *
     * @return the number of points int the set
     */
    public int size() {
        return points.size();
    }

    /**
     * Adds given point to the set of points if it's not already there
     *
     * @param p {@code Point2D} representing given point
     * @see Point2D
     */
    public void insert(Point2D p) {
        points.add(p);
    }

    /**
     * Shows if the set contains given point
     *
     * @param p {@code Point2D} representing given point
     * @return <tt>true</tt> if this set contains given point, otherwise - <tt>false</tt>
     */
    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    /**
     * Draws all of the points in the set to standard draw output
     *
     * @see StdDraw#point(double, double)
     */
    public void draw() {
        points.forEach(point2D -> StdDraw.point(point2D.x(), point2D.y()));
    }

    /**
     * Returns all points int the set which are inside given rectangle
     *
     * @param rect {@code RectHW} representing given rectangle
     * @return {@code Iterable<Point2D>} with the set of points in the rectangle
     * @see RectHV#contains(Point2D)
     */
    public Iterable<Point2D> range(RectHV rect) {
        return points.stream()
                .filter(rect::contains)
                .collect(Collectors.toSet());
    }

    /**
     * Finds the nearest neighbor of the given point in the set
     *
     * @param p {@code Point2D} representing given point
     * @return nearest neighboring point of <tt>null</tt> if set is empty
     * @see Point2D#DISTANCE_TO_ORDER
     */
    public Point2D nearest(Point2D p) {
        return points.stream()
                .min(p.DISTANCE_TO_ORDER)
                .orElse(null);
    }

    public static void main(String[] args) {
        PointSET pointSET = new PointSET();
        Point2D p = new Point2D(0.8, 0.3);
        StdDraw.setPenRadius(0.02);

        // Draws all points and rectangle with Black color
        StdDraw.setPenColor(Color.BLACK);
        for (int i = 0; i < 30; i++) {
            pointSET.insert(new Point2D(StdRandom.uniform(), StdRandom.uniform()));
        }
        RectHV rect = new RectHV(0.2, 0.2, 0.6, 0.6);
        pointSET.draw();
        rect.draw();

        // Draws all points within rectangle with Green color
        StdDraw.setPenColor(Color.GREEN);
        pointSET.range(rect)
                .forEach(point -> StdDraw.point(point.x(), point.y()));

        // Draws the main point with Red color
        StdDraw.setPenColor(Color.RED);
        StdDraw.point(p.x(), p.y());

        // Draws nearest point to main point with Blue color
        StdDraw.setPenColor(Color.BLUE);
        StdDraw.point(pointSET.nearest(p).x(), pointSET.nearest(p).y());

        StdDraw.show(0);
    }
}
