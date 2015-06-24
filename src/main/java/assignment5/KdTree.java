package assignment5;

import edu.princeton.cs.algs4.Point2D;

/**
 * A mutable data type that uses a 2d-tree to implement a set of points in the unit square. A 2d-tree is a
 * generalization of a binary search tree to two-dimensional keys. The idea is to build a BST with points in the nodes,
 * using the x- and y-coordinates of the points as keys in strictly alternating sequence.
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
}
