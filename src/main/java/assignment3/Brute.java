package assignment3;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

import java.util.Arrays;

/**
 * The main goal of {@code Brute.class} is to examine 4 points at a time and to check whether they all lie on the same
 * line segment, printing out any such line segments to standard output and drawing them using standard drawing.
 * To check whether the 4 points p, q, r, and s are collinear, the program checks whether the slopes between p and q,
 * between p and r, and between p and s are all equal.
 * <p>
 * The order of growth of the running time of this program is N^4 in the worst case and it uses space proportional to N.
 * <p>
 * The program takes the name of an input file as a command-line argument, reads the input
 * file (see resources folder for examples), prints to standard output the line segments
 * discovered and draws to standard draw the line segments discovered.
 *
 * @author Alex Ilyenko
 * @see assignment3.Point
 * @see assignment3.Fast
 */
public class Brute {
    /**
     * Constant holding scale size
     */
    private final static int MAX_SCALE_VALUE = 32768;
    /**
     * Constant holding radius for points drawing
     */
    private static final double POINT_RADIUS = 0.02;
    /**
     * Constant holding radius for lines drawing
     */
    private static final double LINE_RADIUS = 0.005;

    public static void main(String[] args) {

        StdDraw.setXscale(0, MAX_SCALE_VALUE);
        StdDraw.setYscale(0, MAX_SCALE_VALUE);
        StdDraw.setPenRadius(POINT_RADIUS);
        In input = new In(args[0]);
        int n = input.readInt();
        Point[] points = new Point[n];
        for (int i = 0; !input.isEmpty(); i++) {
            int x = input.readInt();
            int y = input.readInt();
            Point point = new Point(x, y);
            point.draw();
            points[i] = point;
        }

        StdDraw.setPenRadius(LINE_RADIUS);
        Arrays.sort(points);
        Point p, q, r, s;
        double slopePQ, slopePR, slopePS;
        for (int i = 0, length = points.length; i < length; i++) {
            p = points[i];
            for (int j = i + 1; j < length; j++) {
                q = points[j];
                slopePQ = p.slopeTo(q);
                for (int k = j + 1; k < length; k++) {
                    r = points[k];
                    slopePR = p.slopeTo(r);
                    if (slopePR != slopePQ) {
                        continue;
                    }
                    for (int l = k + 1; l < length; l++) {
                        s = points[l];
                        slopePS = p.slopeTo(s);
                        if (slopePQ == slopePS) {
                            StdOut.printf("%s -> %s -> %s -> %s\n", p, q, r, s);
                            p.drawTo(s);
                        }
                    }
                }
            }
        }
    }
}
