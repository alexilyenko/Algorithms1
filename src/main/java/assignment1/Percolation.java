package assignment1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * This class solves the "real world" Percolation problem
 * using weighted quick union-find algorithm
 *
 * @author Alex Ilyenko
 * @see WeightedQuickUnionUF
 */
public class Percolation {
    /**
     * Variable representing {@code WeightedQuickUnionUF} class
     * which implements weighted quick union-find algorithms
     *
     * @see WeightedQuickUnionUF
     */
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    /**
     * {@code boolean} array representing the indexes of all sites:
     * {@code true} - site is already opened
     * {@code false} - site is closed yet
     */
    private final boolean[] opened;
    /**
     * {@code int} variable representing the grid's side length
     */
    private final int N;
    /**
     * {@code int} variable representing the reserved site's indices.
     * It is used for simplified union with top/bottom row of the grid
     */
    private final int firstReserved, secondReserved;

    /**
     * Takes {@code int} variable and creates N*N grid
     * for solving Percolation problem on it
     *
     * @param N grid's side length
     * @see assignment1.utils.PercolationVisualizer
     * @see WeightedQuickUnionUF#WeightedQuickUnionUF(int)
     */
    public Percolation(int N) {
        this.N = N;
        firstReserved = N * N;
        secondReserved = firstReserved + 1;
        weightedQuickUnionUF = new WeightedQuickUnionUF(secondReserved + 1);
        opened = new boolean[firstReserved];
    }


    /**
     * Shows if the site with given row and column is opened
     *
     * @param y row index
     * @param x column index
     * @return {@code true} if site is opened,
     * {@code false} if it's not
     * @see #validate(int, int)
     */
    public boolean isOpen(int y, int x) {
        validate(x, y);
        return opened[xyTo1D(y, x)];
    }

    /**
     * Opens the site with given row and column
     *
     * @param y row index
     * @param x column index
     * @see #validate(int, int)
     * @see WeightedQuickUnionUF#union(int, int)
     * @see #connectIfIsOpened(int, int)
     */
    public void open(int y, int x) {
        validate(x, y);
        int index = xyTo1D(y, x),
                right = index + 1,
                left = index - 1,
                up = index - N,
                down = index + N;
        opened[index] = true;
        if (y == 1) {
            weightedQuickUnionUF.union(index, firstReserved);
        } else if (y == N) {
            weightedQuickUnionUF.union(index, secondReserved);
        }
        connectIfIsOpened(index, right);
        connectIfIsOpened(index, left);
        connectIfIsOpened(index, up);
        connectIfIsOpened(index, up);
        connectIfIsOpened(index, down);
    }


    /**
     * Shows if the site with given column and row is full with liquid
     *
     * @param y row index
     * @param x column index
     * @return {@code true} if site is full,
     * {@code false} if it's not
     * @see WeightedQuickUnionUF#connected(int, int)
     */
    public boolean isFull(int y, int x) {
        validate(x, y);
        return weightedQuickUnionUF.connected(xyTo1D(y, x), firstReserved);
    }

    /**
     * Shows if the whole grid percolates
     *
     * @return {@code true} if the grid percolates,
     * {@code false} if it doesn't
     * @see WeightedQuickUnionUF#connected(int, int)
     */
    public boolean percolates() {
        return weightedQuickUnionUF.connected(firstReserved, secondReserved);
    }

    /**
     * Connects two sites if they are both opened
     *
     * @param first  index of the first site in 1D array
     * @param second index of the second site in 1D array
     * @see #neighbourIsOpened(int, int)
     * @see WeightedQuickUnionUF#union(int, int)
     */
    private void connectIfIsOpened(int first, int second) {
        if (neighbourIsOpened(first, second)) {
            weightedQuickUnionUF.union(first, second);
        }
    }

    /**
     * Checks if the second site is opened and
     * if it's not already connected to the first one
     *
     * @param first  index of the first site in 1D array
     * @param second index of the second site in 1D array
     * @return {@code true} if the second site is opened,
     * not connected to the first one and is in bounds,
     * {@code false} if one of these conditions fails
     */
    private boolean neighbourIsOpened(int first, int second) {
        return (first > second ? second >= 0 : second < firstReserved)
                && opened[second]
                && !weightedQuickUnionUF.connected(first, second);
    }

    /**
     * Validates row and column indices of the site
     *
     * @param x column index
     * @param y row index
     * @throws IndexOutOfBoundsException if one of the indexes less
     *                                   or equal to zero or more than grid's side size
     */
    private void validate(int x, int y) {
        if (x <= 0 || x > N || y <= 0 || y > N) {
            throw new IndexOutOfBoundsException("one of the indexes is out of bounds");
        }
    }

    /**
     * Converts 2D array indices of the site two 1D array index
     *
     * @param y row index of the site
     * @param x column index of the site
     * @return site's index in 1D array
     */
    private int xyTo1D(int y, int x) {
        return N * (y - 1) + x - 1;
    }
}