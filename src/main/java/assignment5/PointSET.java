package assignment5;

import edu.princeton.cs.algs4.Point2D;

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
    private final Set<Point2D> points = new TreeSet<>();

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
     * @return the number of points in the set
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
     * @see Point2D#draw()
     */
    public void draw() {
        points.forEach(Point2D::draw);
    }

    /**
     * Returns all points in the set which are inside given rectangle
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

}