package assignment4;

import java.util.Arrays;

/**
 * Created by oilyenko on 5/29/2015.
 */
public class Board {
    private final int N;
    private final byte[][] tiles;

    public Board(int[][] blocks) {
        N = blocks.length;
        if (blocks[0].length != N) {
            throw new IllegalArgumentException("Board's dimensions should have equal sizes!");
        }
        tiles = new byte[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = (byte) blocks[i][j];
            }
        }
    }           // construct a board from an N-by-N array of blocks

    // (where blocks[i][j] = block in row i, column j)
    public int dimension() {
        return N;
    }                 // board dimension N

    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == N - 1 && j == N - 1) {
                    break;
                }
                if (tiles[i][j] != j + 1 + i * N) {
                    hamming++;
                }
            }
        }
        return hamming;
    }                // number of blocks out of place

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != j + 1 + i * N) {
                    manhattan += countMoves(i, j);
                }
            }
        }
        return manhattan;
    }// sum of Manhattan distances between blocks and goal

    private int countMoves(int i, int j) {
        int val = tiles[i][j] - 1;
        if (val < 0) {
            return 0;
        }
        return Math.abs(j - (val - (val / N) * N)) + Math.abs(i - val / N);
    }

    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == N - 1 && j == N - 1) {
                    break;
                }
                if (tiles[i][j] != j + 1 + i * N) {
                    return false;
                }
            }
        }
        return true;
    }              // is this board the goal board?

    public Board twin() {
        return null;
    }     // a board obtained by exchanging two adjacent blocks in the same row

    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;
        return Arrays.deepEquals(this.tiles, that.tiles);

    }      // does this board equal y?

    public Iterable<Board> neighbors() {
        return null;
    } // all neighboring boards

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N).append("\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        Board board = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        Board a = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        System.out.println(board.equals(a));
    }
}
