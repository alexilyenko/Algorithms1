package assignment1;

import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

/**
 * Data type which performs T independent experiments on an N-by-N grid and measures sample mean,
 * standard deviation, low and high endpoints of 95% confidence interval
 *
 * @author Alex Ilyenko
 */
public class PercolationStats {
    /**
     * Array that stores estimates of each percolation threshold
     */
    private final double[] results;

    /**
     * Number of experiments
     */
    private final int T;

    /**
     * Constant holding default multiplier for low and high endpoints
     * of 95% confidence interval calculations
     */
    private static final double DEFAULT_MULTIPLIER = 1.96;

    /**
     * Creates {@code PercolationStats} object and performs T independent
     * experiments on an N-by-N grid
     *
     * @param N grid side length
     * @param T number of independent experiments
     * @throws IllegalArgumentException if one of the parameters is less or equal to zero
     * @see Percolation
     * @see Percolation#Percolation(int)
     * @see Percolation#open(int, int)
     * @see Percolation#percolates()
     * @see StdRandom#uniform(double, double)
     */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T should be greater than zero!");
        }
        this.T = T;
        results = new double[T];
        int square = N * N;
        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(N);
            double numberOfOpenedCells = 0;
            while (!percolation.percolates()) {
                int x = StdRandom.uniform(1, N + 1);
                int y = StdRandom.uniform(1, N + 1);
                if (!percolation.isOpen(y, x)) {
                    percolation.open(y, x);
                    numberOfOpenedCells++;
                }
            }
            results[i] = numberOfOpenedCells / square;
        }
    }

    /**
     * Calculates mean of percolation threshold
     *
     * @return {@code double} value of mean
     * @see edu.princeton.cs.introcs.StdStats#mean(double[])
     */
    public double mean() {
        return StdStats.mean(results);
    }

    /**
     * Calculates standard deviation of percolation threshold
     *
     * @return {@code double} value of standard deviation
     * @see edu.princeton.cs.introcs.StdStats#stddev(double[])
     */
    public double stddev() {
        return StdStats.stddev(results);
    }

    /**
     * Calculates low endpoint of 95% confidence interval
     *
     * @return {@code double} value of low endpoint
     * @see #mean()
     * @see #stddev()
     */
    public double confidenceLo() {
        return mean() - DEFAULT_MULTIPLIER * stddev() / Math.sqrt(T);
    }

    /**
     * Calculates high endpoint of 95% confidence interval
     *
     * @return {@code double} value of high endpoint
     * @see #mean()
     * @see #stddev()
     */
    public double confidenceHi() {
        return mean() + DEFAULT_MULTIPLIER * stddev() / Math.sqrt(T);
    }

    /**
     * Prints mean, standard deviation, low and high endpoints of 95% confidence interval
     * of the percolation threshold to the console
     *
     * @see #mean()
     * @see #stddev()
     * @see #confidenceLo()
     * @see #confidenceHi()
     */
    private void info() {
        StdOut.printf("mean\t\t\t\t\t= %f\nstddev\t\t\t\t\t= %f\n95%% confidence interval\t= %f, %f\n",
                mean(), stddev(), confidenceLo(), confidenceHi());
    }

    /**
     * Main method that prints percolation stats to console
     * <p/>
     * Examples of input and output:
     * % java PercolationStats 200 100
     * mean                    = 0.5929934999999997
     * stddev                  = 0.00876990421552567
     * 95% confidence interval = 0.5912745987737567, 0.5947124012262428
     * <p/>
     * % java PercolationStats 200 100
     * mean                    = 0.592877
     * stddev                  = 0.009990523717073799
     * 95% confidence interval = 0.5909188573514536, 0.5948351426485464
     * <p/>
     * % java PercolationStats 2 10000
     * mean                    = 0.666925
     * stddev                  = 0.11776536521033558
     * 95% confidence interval = 0.6646167988418774, 0.6692332011581226
     * <p/>
     * % java PercolationStats 2 100000
     * mean                    = 0.6669475
     * stddev                  = 0.11775205263262094
     * 95% confidence interval = 0.666217665216461, 0.6676773347835391
     *
     * @param args array holding args[0] - N (grid side size)
     *             and args[1] - T (number of independent computational experiments )
     */
    public static void main(String[] args) {
        new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1])).info();
    }

}
