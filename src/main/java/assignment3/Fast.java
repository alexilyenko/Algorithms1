package assignment3;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * The main goal of {@code Fast.class} is to examine 4 or more points at a time and to check whether they all lie on the same
 * line segment, printing out any such line segments to standard output and drawing them using standard drawing.
 * To check whether the points p, q, r, and s are collinear, the program checks whether the slopes between p and q,
 * between p and r, and between p and s are all equal.
 * <p>
 * The order of growth of the running time of the program is N^2*log N in the worst case and
 * it should use space proportional to N. So you can see that the given program is much faster
 * than {@link assignment3.Brute}. One of the most important changes is self-implemented Merge-Sort and Insertion-Sort
 * algorithms for ordering points according to their slopes.
 * <p>
 * The program takes the name of an input file as a command-line argument, reads the input
 * file (see resources folder for examples), prints to standard output the line segments
 * discovered and draws to standard draw the line segments discovered
 *
 * @author Alex Ilyenko
 * @see assignment3.Point
 * @see assignment3.Brute
 */
public class Fast {
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
    /**
     * Constant holding boundary value for using insertion or merge sort.
     * If array has more than #CUTOFF elements, it will be sorted using merge sorting algorithms,
     * otherwise it will be sorted using insertion sorting one
     */
    private static final int CUTOFF = 7;
    /**
     * Constant holding natural ordering {@code Comparator} for {@code Point}s' comparing
     *
     * @see java.util.Comparator
     */
    private static final Comparator<Point> POINT_NATURAL_ORDER = Point::compareTo;


    /**
     * Main method
     *
     * @param args array where args[0] represents path of the file with points' coordinates
     * @see assignment3.Point
     */
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
        mergeSort(points, POINT_NATURAL_ORDER);
        Point q, r, s, first;
        double slopePQ;
        Point[] slopeOrdered;
        LinkedList<Point> segment = new LinkedList<>();
        for (Point p : points) {
            slopeOrdered = points.clone();
            mergeSort(slopeOrdered, p.SLOPE_ORDER);
            for (int i = 1, length = slopeOrdered.length; i < length - 2; i++) {
                q = slopeOrdered[i];
                slopePQ = p.slopeTo(q);
                r = slopeOrdered[i + 1];
                s = slopeOrdered[i + 2];
                if (slopePQ == p.slopeTo(r) && slopePQ == p.slopeTo(s)) {
                    i += 2;
                    segment.addLast(p);
                    segment.addLast(q);
                    segment.addLast(r);
                    segment.addLast(s);
                    while (i + 1 < length && slopePQ == p.slopeTo(slopeOrdered[i + 1])) {
                        segment.addLast(slopeOrdered[++i]);
                    }
                    first = segment.pollFirst();
                    StdOut.print(first);
                    segment.forEach((point) -> StdOut.print(" -> " + point));
                    StdOut.println();
                    first.drawTo(segment.getLast());
                    segment.clear();
                }

            }
        }

    }


    /**
     * Sorts the array using Merge-Sorting algorithm
     *
     * @param array given array for sorting
     * @param c     given {@code Comparator} used for ordering of the items in array
     * @param <T>   generic data type
     */
    private static <T> void mergeSort(T[] array, Comparator<T> c) {
        int length = array.length;
        @SuppressWarnings("unchecked") T[] aux = (T[]) new Object[length];
        sort(array, aux, 0, length - 1, c);
    }

    /**
     * Recursively sorts the part of the array
     *
     * @param array given array for sorting
     * @param aux   auxiliary array for data transferring while sorting
     * @param lo    low boundary of array's part to be sorted
     * @param hi    bottom boundary of array's part to be sorted
     * @param c     given {@code Comparator} used for ordering of the items in array
     * @param <T>   generic data type
     */
    private static <T> void sort(T[] array, T[] aux, int lo, int hi, Comparator<T> c) {
        if (hi <= lo + CUTOFF - 1) {
            insertionSort(array, lo, hi, c);
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(array, aux, lo, mid, c);
        sort(array, aux, mid + 1, hi, c);
        if (c.compare(array[mid + 1], array[mid]) == 1) {
            return;
        }
        merge(array, aux, lo, mid, hi, c);
    }

    /**
     * Merges two sorted parts of the array
     *
     * @param array given array to be sorted
     * @param aux   auxiliary array for data transferring while sorting
     * @param lo    low boundary of the first sorted array's part
     * @param mid   bottom boundary of the first sorted array's part and low boundary of the second one
     * @param hi    bottom boundary of the second sorted array's part
     * @param c     given {@code Comparator} used for ordering of the items in array
     * @param <T>   generic data type
     */
    private static <T> void merge(T[] array, T[] aux, int lo, int mid, int hi, Comparator<T> c) {
        System.arraycopy(array, lo, aux, lo, hi + 1 - lo);
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                array[k] = aux[j++];
            } else if (j > hi) {
                array[k] = aux[i++];
            } else if (c.compare(aux[i], aux[j]) == 1) {
                array[k] = aux[j++];
            } else {
                array[k] = aux[i++];
            }
        }
    }

    /**
     * Sorts the part of the array using Insertion Sort algorithms
     *
     * @param array      given array for sorting
     * @param lo         low boundary of array's part to be sorted
     * @param hi         bottom boundary of array's part to be sorted
     * @param comparator given {@code Comparator} used for ordering of the items in array
     * @param <T>        generic data type
     */
    private static <T> void insertionSort(T[] array, int lo, int hi, Comparator<T> comparator) {
        T newValue;
        int i, j;
        for (i = lo + 1; i < hi + 1; i++) {
            newValue = array[i];
            j = i;
            while (j > lo && comparator.compare(array[j - 1], newValue) == 1) {
                array[j] = array[j - 1];
                j--;
            }
            array[j] = newValue;
        }
    }

}
