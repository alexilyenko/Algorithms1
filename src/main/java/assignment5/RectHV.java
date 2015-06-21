package assignment5;

import edu.princeton.cs.algs4.Point2D;

/**
 * Created by alex on 21.06.15.
 */
public class RectHV {
    public RectHV(double xmin, double ymin,         // construct the rectangle [xmin, xmax] x [ymin, ymax]
                  double xmax, double ymax) {
    }        // throw a java.lang.IllegalArgumentException if (xmin > xmax) or (ymin > ymax)

    public double xmin() {
        return 0;
    }                           // minimum x-coordinate of rectangle

    public double ymin() {
        return 0;
    }                 // minimum y-coordinate of rectangle

    public double xmax() {
        return 0;
    }                            // maximum x-coordinate of rectangle

    public double ymax() {
        return 0;
    }                            // maximum y-coordinate of rectangle

    public boolean contains(Point2D p){
        return false;
    }              // does this rectangle contain the point p (either inside or on boundary)?

    public boolean intersects(RectHV that) {
        return false;
    }         // does this rectangle intersect that rectangle (at one or more points)?

    public double distanceTo(Point2D p) {
        return 0;
    }            // Euclidean distance from point p to the closest point in rectangle

    public double distanceSquaredTo(Point2D p) {
        return 0;
    }      // square of Euclidean distance from point p to closest point in rectangle

    @Override
    public boolean equals(Object that){
        return false;
    }              // does this rectangle equal that?

    public void draw() {

    }                             // draw to standard draw

    @Override
    public String toString(){
        return null;
    }                        // string representation

}
