package assignment5;

import edu.princeton.cs.algs4.Point2D;

/**
 * Created by alex on 21.06.15.
 */
public class PointSET {
    public PointSET() {
    }                               // construct an empty set of points

    public boolean isEmpty() {
        return false;
    }                        // is the set empty?

    public int size() {
        return 0;
    }                     // number of points in the set

    public void insert(Point2D p) {

    }                  // add the point p to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        return false;
    }             // does the set contain the point p?

    public void draw() {
    }                              // draw all of the points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }     // all points in the set that are inside the rectangle

    public Point2D nearest(Point2D p) {
        return null;
    }               // a nearest neighbor in the set to p; null if set is empty
}
