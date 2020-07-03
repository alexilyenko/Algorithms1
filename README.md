Algorithms Part I
=====================

Code for programming assignments in Java from the Coursera course, [Algorithms Part I](https://www.coursera.org/learn/algorithms-part1).

Week 1 - Union / Percolation
--------------------------------
 - **Percolation.java** - A model for the percolation problem, determines if a 2d system of open / closed sites percolates from top to bottom.
 - **PercolationStats.java** - Generates statistics using the percolation model.

Week 2 - Queues, Stacks and Bags
--------------------------------
 - **Deque.java** - A generic double ended queue implementation, array based.
 - **RandomizedQueue.java** - A generic random queue implementation, array based.
 - **Subset.java** - Prints n number of random strings provided through standard input.

Week 3 - Collinear Points
--------------------------------
 - **Point.java** - A simple point class.
 - **Brute.java** - A N^4 algorithm for calculating 4 collinear points.
 - **Fast.java** - A faster implementation (N^2*log N) for finding collinear points.

Week 4 - 8 Puzzle
--------------------------------
 - **Board.java** - Represents a sliding puzzle board.
 - **Solver.java** - Uses A* algorithm to find solution to the puzzle board.

Week 5 - KdTree
--------------------------------
 - **PointSet.java** - A set of points on a 2D Euclidian plane, some simple function like #nearest and #contains.
 - **KdTree.java** - Uses a 2d tree algorithm to more efficiently perform functions such as #nearest and #contains.
 - **RectHV.java** - Immutable data type representing axis-aligned rectangle.

Requires the stdlib.jar and algs4.jar to run, available in Maven repository. See given pom file.
