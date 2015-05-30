package assignment2;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

/**
 * Client program that takes a command-line integer k, reads in a sequence of N strings from standard input
 * and prints out exactly k of them, uniformly at random.
 * Each item from the sequence can be printed out at most once.
 * Assume that 0 ≤ k ≤ N, where N is the number of string on standard input.
 * Examples of input and output:
 * <p>
 * % echo A B C D E F G H I | java Subset 3
 * C
 * G
 * A
 * CC
 * <p>
 * % echo A B C D E F G H I | java Subset 3
 * E
 * F
 * G
 * <p>
 * % echo AA BB BB BB BB BB CC CC | java Subset 8
 * BB
 * AA
 * BB
 * BB
 * BB
 * CC
 * BB
 *
 * @see RandomizedQueue
 */
public class Subset {
    /**
     * Main method
     *
     * @param args array where args[0] represents k (number of items to print)
     * @see RandomizedQueue#enqueue(Object)
     * @see RandomizedQueue#dequeue()
     */
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
        }
        int times = Integer.parseInt(args[0]);
        for (int i = 0; i < times; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }


    }
}
