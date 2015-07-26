package assignment5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.introcs.StdDraw;

import java.util.Set;
import java.util.TreeSet;

/**
 * A mutable data type that uses a 2d-tree to implement a set of points in the unit square. A 2d-tree is a
 * generalization of a binary search tree to two-dimensional keys. The idea is to build a BST with points in
 * the nodes, using the x- and y-coordinates of the points as keys in strictly alternating sequence.
 * <p>
 * The prime advantage of a 2d-tree over a binary search tree (implemented in {@link PointSET}) is that it supports
 * efficient implementation of range search and nearest neighbor search. Each node corresponds to an axis-aligned
 * rectangle in the unit square, which encloses all of the points in its subtree. The root corresponds to the unit
 * square; the left and right children of the root corresponds to the two rectangles split by the x-coordinate of the
 * point at the root; and so forth.
 *
 * @author Alex Ilyenko
 * @see PointSET
 * @see Point2D
 * @see RectHV
 * @see <a href="https://en.wikipedia.org/wiki/K-d_tree">k-d tree structures on Wikipedia</a>
 */
public class KdTree {

    private static class Node {
        private Node leftNode;
        private Node rightNode;
        private final boolean isVertical;
        private final double x;
        private final double y;

        private Node(double x, double y, Node leftNode, Node rightNode, boolean isVertical) {
            this.x = x;
            this.y = y;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
            this.isVertical = isVertical;
        }
    }

    private static final RectHV FIELD = new RectHV(0, 0, 1, 1);
    private Node root;
    private int size;

    /**
     * Constructs an empty Kd Tree
     */
    public KdTree() {
        this.size = 0;
        this.root = null;
    }

    /**
     * Shows if the Kd Tree of points empty
     *
     * @return <tt>true</tt> if this set contains no elements, otherwise - <tt>false</tt>
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Indicates the number of points in the Kd Tree
     *
     * @return the number of points int the set
     */
    public int size() {
        return this.size;
    }

    /**
     * Adds given point to the Kd Tree if it's not already there
     *
     * @param p {@code Point2D} representing given point
     * @see Point2D
     */
    public void insert(Point2D p) {
        this.root = insert(this.root, p, true);
    }

    private Node insert(Node node, Point2D p, boolean isVertical) {
        // if new node, create it  
        if (node == null) {
            size++;
            return new Node(p.x(), p.y(), null, null, isVertical);
        }

        // if already in, return it  
        if (node.x == p.x() && node.y == p.y()) {
            return node;
        }

        // insert it where corresponds (left - right recursive call)  
        if (node.isVertical && p.x() < node.x || !node.isVertical
                && p.y() < node.y) {
            node.leftNode = insert(node.leftNode, p, !node.isVertical);
        } else {
            node.rightNode = insert(node.rightNode, p, !node.isVertical);
        }
        return node;
    }

    /**
     * Shows if the Kd Tree contains given point
     *
     * @param p {@code Point2D} representing given point
     * @return <tt>true</tt> if this set contains given point, otherwise - <tt>false</tt>
     */
    public boolean contains(Point2D p) {
        return contains(root, p.x(), p.y());
    }

    private boolean contains(Node node, double x, double y) {
        if (node == null) {
            return false;
        }

        if (node.x == x && node.y == y) {
            return true;
        }

        if (node.isVertical && x < node.x || !node.isVertical && y < node.y) {
            return contains(node.leftNode, x, y);
        } else {
            return contains(node.rightNode, x, y);
        }
    }

    /**
     * Draws all of the points in the set to standard draw output
     */
    public void draw() {
        StdDraw.setScale(0, 1);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        FIELD.draw();

        draw(root, FIELD);
    }

    private void draw(Node node, RectHV rect) {
        if (node == null) {
            return;
        }

        // draw the point  
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.x, node.y);

        // get the min and max points of division line  
        Point2D min, max;
        if (node.isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            min = new Point2D(node.x, rect.ymin());
            max = new Point2D(node.x, rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            min = new Point2D(rect.xmin(), node.y);
            max = new Point2D(rect.xmax(), node.y);
        }

        // draw that division line  
        StdDraw.setPenRadius();
        min.drawTo(max);

        // recursively draw children  
        draw(node.leftNode, getLeftRect(rect, node));
        draw(node.rightNode, getRightRect(rect, node));
    }

    private RectHV getLeftRect(RectHV rect, Node node) {
        if (node.isVertical) {
            return new RectHV(rect.xmin(), rect.ymin(), node.x, rect.ymax());
        }
        return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.y);
    }

    private RectHV getRightRect(RectHV rect, Node node) {
        if (node.isVertical) {
            return new RectHV(node.x, rect.ymin(), rect.xmax(), rect.ymax());
        }
        return new RectHV(rect.xmin(), node.y, rect.xmax(), rect.ymax());
    }

    /**
     * Returns all points int the set which are inside given rectangle
     *
     * @param rect {@code RectHW} representing given rectangle
     * @return {@code Iterable<Point2D>} with the set of points in the rectangle
     * @see RectHV#contains(Point2D)
     */
    public Iterable<Point2D> range(RectHV rect) {
        Set<Point2D> pointsInRange = new TreeSet<>();
        range(root, FIELD, rect, pointsInRange);
        return pointsInRange;
    }

    private void range(Node node, RectHV nodeRect, RectHV rect, Set<Point2D> pointsInRange) {
        if (node == null) {
            return;
        }

        if (rect.intersects(nodeRect)) {
            Point2D nodePoint = new Point2D(node.x, node.y);
            if (rect.contains(nodePoint)) {
                pointsInRange.add(nodePoint);
            }

            range(node.leftNode, getLeftRect(nodeRect, node), rect, pointsInRange);
            range(node.rightNode, getRightRect(nodeRect, node), rect, pointsInRange);
        }
    }

    /**
     * Finds the nearest neighbor of the given point in the set
     *
     * @param p {@code Point2D} representing given point
     * @return nearest neighboring point of <tt>null</tt> if set is empty
     */
    public Point2D nearest(Point2D p) {
        return nearest(root, FIELD, p.x(), p.y(), null);
    }

    private Point2D nearest(Node node, RectHV rect, double x, double y, Point2D candidate) {
        if (node == null) {
            return candidate;
        }

        double queryToNearest = 0.0;
        double rectToQuery = 0.0;
        RectHV left, right;
        Point2D queryPoint = new Point2D(x, y);
        Point2D nearest = candidate;

        if (nearest != null) {
            queryToNearest = queryPoint.distanceSquaredTo(nearest);
            rectToQuery = rect.distanceSquaredTo(queryPoint);
        }

        if (nearest == null || queryToNearest > rectToQuery) {
            Point2D point = new Point2D(node.x, node.y);
            if (nearest == null || queryToNearest > queryPoint.distanceSquaredTo(point)) {
                nearest = point;
            }

            double xMin = rect.xmin();
            double yMin = rect.ymin();
            double xMax = rect.xmax();
            double yMax = rect.ymax();
            if (node.isVertical) {
                left = new RectHV(xMin, yMin, node.x, yMax);
                right = new RectHV(node.x, yMin, xMax, yMax);

                if (x < node.x) {
                    nearest = nearest(node.leftNode, left, x, y, nearest);
                    nearest = nearest(node.rightNode, right, x, y, nearest);
                } else {
                    nearest = nearest(node.rightNode, right, x, y, nearest);
                    nearest = nearest(node.leftNode, left, x, y, nearest);
                }
            } else {
                left = new RectHV(xMin, yMin, xMax, node.y);
                right = new RectHV(xMin, node.y, xMax, yMax);

                if (y < node.y) {
                    nearest = nearest(node.leftNode, left, x, y, nearest);
                    nearest = nearest(node.rightNode, right, x, y, nearest);
                } else {
                    nearest = nearest(node.rightNode, right, x, y, nearest);
                    nearest = nearest(node.leftNode, left, x, y, nearest);
                }
            }
        }

        return nearest;
    }
}  