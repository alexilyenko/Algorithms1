package assignment4;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Data structure representing the state of a two-dimensional NxN sliding puzzle board
 *
 * @author Alex Ilyenko
 */
public class Board {
    /**
     * {@code StringBuilder} instance for optimized {@code #toString} calling
     *
     * @see StringBuilder
     * @see #toString()
     */
    private static StringBuilder stringBuilder = new StringBuilder();
    /**
     * Board's dimension size
     */
    private final int N;
    /**
     * 2D array representing all blocks on the board. Block with zero value is empty
     */
    private final byte[][] tiles;
    /**
     * Value representing Manhattan priority function calculation result.
     * Initial value (before calculation) is -1
     */
    private int manhattan = -1;
    /**
     * Value representing Hamming priority function calculation result.
     * Initial value (before calculation) is -1
     */
    private int hamming = -1;
    /**
     * {@code Block} instance representing empty block
     *
     * @see assignment4.Board.Block
     */
    private Block zeroBlock;

    /**
     * The number of moves made on the board
     */
    private int moves = 0;

    /**
     * Constructs a board from an N-by-N array of blocks,
     * where blocks[i][j] = block in row i, column j
     *
     * @param blocks 2d {@code int} array with cells representing blocks
     * @throws IllegalArgumentException if dimensions have different sizes
     */
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
    }

    /**
     * Private constructor for creation twins and neighbors of the {@code Board}
     *
     * @param blocks 2d {@code byte} array with cells representing blocks
     * @see #twin()
     * @see #neighbors()
     */
    private Board(byte[][] blocks, int moves) {
        tiles = blocks;
        N = blocks.length;
        this.moves = moves;
    }


    /**
     * Gets board dimension
     *
     * @return board dimension's size
     * @see #N
     */
    public int dimension() {
        return N;
    }

    /**
     * Calculates Hamming priority function.
     * <p>
     * Explanation of the function:
     * The number of blocks in the wrong position, plus the
     * number of moves made so far to get to the search node. Intuitively, a search node
     * with a small number of blocks in the wrong position is close to the goal, and we
     * prefer a search node that have been reached using a small number of moves.
     * For example:
     * <p>
     * 8  1  3        1  2  3     1  2  3  4  5  6  7  8
     * 4     2        4  5  6     ----------------------
     * 7  6  5        7  8        1  1  0  0  1  1  0  1
     * <p>
     * initial          goal         Hamming = 5 + 0
     *
     * @return the number of blocks out of place
     * @see #calcHamming()
     * @see <http://en.wikipedia.org/wiki/Hamming_distance>Hamming priority function on Wikipedia</a>
     */
    public int hamming() {
        if (hamming < 0) {
            hamming = calcHamming();
        }
        return hamming;
    }

    /**
     * Internal function for calculation Hamming priority function.
     * It is called only if needed, usually on the first time
     * of {@link #hamming()} method invoking.
     *
     * @return Hamming function calculation result
     * @see #hamming()
     */
    private int calcHamming() {
        int hamming = moves;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // last tile is the place for empty block
                if (i == N - 1 && j == N - 1) {
                    break;
                }
                if (tiles[i][j] != j + 1 + i * N) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    /**
     * Calculates Manhattan priority function.
     * <p>
     * Explanation of the function:
     * The sum of the Manhattan distances (sum of the vertical and horizontal distance) from
     * the blocks to their goal positions, plus the number of moves made so far to get to
     * the search node.
     * For example:
     * <p>
     * 8  1  3        1  2  3     1  2  3  4  5  6  7  8
     * 4     2        4  5  6     ----------------------
     * 7  6  5        7  8        1  2  0  0  2  2  0  3
     * <p>
     * initial          goal        Manhattan = 10 + 0
     *
     * @return sum of Manhattan distances between blocks and goal
     * @see #calcManhattan()
     * @see <http://en.wikipedia.org/wiki/Taxicab_geometry>Manhattan priority function on Wikipedia</a>
     */
    public int manhattan() {
        if (manhattan < 0) {
            manhattan = calcManhattan();
        }
        return manhattan;
    }

    /**
     * Internal function for calculation Manhattan priority function.
     * It is called only if needed, usually on the first time
     * of {@link #manhattan()}  method invoking.
     *
     * @return Manhattan function calculation result
     * @see #countMoves(int, int)
     * @see #manhattan()
     */
    private int calcManhattan() {
        int manhattan = moves;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != j + 1 + i * N) {
                    manhattan += countMoves(i, j);
                }
            }
        }
        return manhattan;
    }

    /**
     * Counts number of moves for some block to get to desirable
     * position from given one
     *
     * @param i y-coordinate of the given block
     * @param j x-coordinate of the given block
     * @return number of calculated moves
     */
    private int countMoves(int i, int j) {
        int val = tiles[i][j] - 1;
        if (val < 0) {
            zeroBlock = new Block(i, j);
            return 0;
        }
        return Math.abs(j - (val - (val / N) * N)) + Math.abs(i - val / N);
    }

    /**
     * Checks if the board is goal board
     * For example for board 3x3 the goal is:
     * <p>
     * 1  2  3
     * 4  5  6
     * 7  8
     *
     * @return {@code true} if goal is reached, otherwise {@code false}
     */
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
    }

    /**
     * Gets the board obtained by exchanging two adjacent blocks in the same row.
     * It is used to determine whether a puzzle is solvable: exactly one of a board and
     * its twin are solvable. A twin is obtained by swapping two adjacent blocks (the blank
     * does not count) in the same row.
     * For example, here is a board and its 5 possible twins. The solver will use only one twin.
     * <p>
     * 1  3       3  1       1  3       1  3       1  3       1  3
     * 4  2  5    4  2  5    2  4  5    4  5  2    4  2  5    4  2  5
     * 7  8  6    7  8  6    7  8  6    7  8  6    8  7  6    7  6  8
     * <p>
     * board      twin       twin       twin       twin       twin
     *
     * @return the result twin
     * @throws RuntimeException if twin can not be found
     * @see #cloneTiles()
     * @see #exchange(byte[][], int, int, int, int)
     */
    public Board twin() {
        byte[][] copy = cloneTiles();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - 1; j++) {
                // finds not empty adjacent blocks
                if (copy[i][j] > 0 && copy[i][j + 1] > 0) {
                    return new Board(exchange(copy, i, j, i, j + 1), moves);
                }
            }
        }
        throw new RuntimeException("Could not make a twin!");

    }

    /**
     * Clones {@link #tiles} array of blocks
     *
     * @return copy of the block array for current board
     * @see #clone()
     */
    private byte[][] cloneTiles() {
        byte[][] copy = tiles.clone();
        for (int i = 0; i < N; i++) {
            copy[i] = tiles[i].clone();
        }
        return copy;
    }

    /**
     * Exchanges cells (blocks) in the given two-dimensional array
     *
     * @param array   given 2-dimensional {@code byte} array
     * @param firstY  y-coordinate of the first cell
     * @param firstX  x-coordinate of the first cell
     * @param secondY y-coordinate of the second cell
     * @param secondX x-coordinate of the second cell
     * @return given array with already exchanged cells
     */
    private byte[][] exchange(byte[][] array, int firstY, int firstX, int secondY, int secondX) {
        byte tmp = array[firstY][firstX];
        array[firstY][firstX] = array[secondY][secondX];
        array[secondY][secondX] = tmp;
        return array;
    }

    /**
     * Detects all possible neighboring boards of the current one.
     * Neighbors are those boards which can be reached in one move of the block to empty place
     * For example:
     * <p>
     * 8  1  3       8  1       8  1  3     8  1  3
     * 4  2          4  2  3    4     2     4  2  5
     * 7  6  5       7  6  5    7  6  5     7  6
     * <p>
     * search node   neighbor   neighbor    neighbor
     *
     * @return {@code Set} (i.e. order is not guaranteed) of the all possible neighboring boards
     * @see #getZeroBlock()
     * @see #exchange(byte[][], int, int, int, int)
     * @see #cloneTiles()
     */
    public Iterable<Board> neighbors() {
        Set<Board> boardSet = new HashSet<>();
        Block zero = getZeroBlock();
        moves++;
        if (zero.x + 1 < N) {
            boardSet.add(new Board(exchange(cloneTiles(), zero.y,
                    zero.x, zero.y, zero.x + 1), moves));
        }
        if (zero.x - 1 >= 0) {
            boardSet.add(new Board(exchange(cloneTiles(), zero.y,
                    zero.x, zero.y, zero.x - 1), moves));
        }

        if (zero.y + 1 < N) {
            boardSet.add(new Board(exchange(cloneTiles(), zero.y,
                    zero.x, zero.y + 1, zero.x), moves));
        }

        if (zero.y - 1 >= 0) {
            boardSet.add(new Board(exchange(cloneTiles(), zero.y,
                    zero.x, zero.y - 1, zero.x), moves));
        }
        return boardSet;
    }

    /**
     * Finds and returns empty block
     *
     * @return {@code Block} instance
     * @see #findZeroBlock()
     * @see assignment4.Board.Block
     */
    private Block getZeroBlock() {
        if (zeroBlock == null) {
            zeroBlock = findZeroBlock();
        }
        return zeroBlock;
    }

    /**
     * Finds empty block coordinates
     *
     * @return {@code Block} instance
     * @throws RuntimeException if empty block can not be found
     * @see assignment4.Board.Block
     */
    private Block findZeroBlock() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    return new Block(i, j);
                }
            }
        }
        throw new RuntimeException("Zero value is not found!");
    }

    /**
     * Private data structure representing block with given coordinates
     */
    private class Block {
        private final int x;
        private final int y;

        private Block(int y, int x) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Indicates whether some other object equal to current {@code Board}
     *
     * @param y given object to check for equality
     * @return {@code true} if given object is equal to current {@code Board},
     * otherwise returns {@code false}
     */
    @Override
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
        return this.dimension() == that.dimension() && Arrays.deepEquals(this.tiles, that.tiles);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tiles);
    }

    /**
     * Returns String representation of the current {@code Board}
     * Example of result String for 3x3 size board:
     * <p>
     * 3
     * 8  1  3
     * 4     2
     * 7  6  5
     * ,<p>
     * where first row represents dimension size and others represent
     * graphical view of the board
     *
     * @return {@code String} representing all details of the board
     */
    @Override
    public String toString() {
        stringBuilder.setLength(0);
        stringBuilder.append(N).append("\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                stringBuilder.append(String.format("%2d ", tiles[i][j]));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        Board board = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        System.out.println(board);

    }
}
