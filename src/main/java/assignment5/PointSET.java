package assignment5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdRandom;

import java.awt.*;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 *
 */
public class PointSET {
    private final Set<Point2D> set;

    public PointSET() {
        set = new TreeSet<>();
    }                               // construct an empty set of points

    public boolean isEmpty() {
        return set.isEmpty();
    }                        // is the set empty?

    public int size() {
        return set.size();
    }                     // number of points in the set

    public void insert(Point2D p) {
        set.add(p);
    }                  // add the point p to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        return set.contains(p);
    }             // does the set contain the point p?

    public void draw() {
        set.forEach(point2D -> StdDraw.point(point2D.x(), point2D.y()));
    }                              // draw all of the points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        return set.stream()
                .filter(rect::contains)
                .collect(Collectors.toSet());
    }     // all points in the set that are inside the rectangle

    public Point2D nearest(Point2D p) {
        return set.stream()
                .min(p.DISTANCE_TO_ORDER)
                .orElse(null);
    }
    // a nearest neighbor in the set to p; null if set is empty

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
