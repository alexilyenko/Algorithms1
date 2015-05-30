package assignment4;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * This class's main goal is to solve the so called 'Puzzle Problem' using A* search algorithm.
 * The solving method based on Manhattan priority function calculation of the puzzle board.
 *
 * @author Alex Ilyenko
 * @see <a href="http://en.wikipedia.org/wiki/A*_search_algorithm">A* search algorithm on Wikipedia</a>
 * @see <a href="http://en.wikipedia.org/wiki/15_puzzle">Puzzle problem on Wikipedia</a>
 */
public class Solver {
    /**
     * Custom comparator which compares Manhattan priority function values of two given {@code Board}s
     *
     * @see Comparator
     * @see Board#manhattan()
     */
    private final Comparator<Node> boardComparator = (a, b) -> a.board.manhattan() - b.board.manhattan();
    /**
     * Priority queue instance for the given board and its possible moves. The first-out in this queue is
     * the board with minimal Manhattan priority function value
     *
     * @see #boardComparator
     */
    private final MinPQ<Node> moves = new MinPQ<>(boardComparator);
    /**
     * Priority queue instance for the twin of the given board and its possible moves. The first-out
     * in this queue is the board with minimal Manhattan priority function value
     *
     * @see #boardComparator
     * @see Board#twin()
     */
    private final MinPQ<Node> twinMoves = new MinPQ<>(boardComparator);
    /**
     * Boolean flag representing whether the given board is solvable
     */
    private boolean solvable = false;
    /**
     * Boolean flag representing whether the twin of the given board is solvable
     *
     * @see Board#twin()
     */
    private boolean twinSolvable = false;
    /**
     * {@code Node} with already solved {@code Board}
     *
     * @see assignment4.Solver.Node
     */
    private Node solved;

    /**
     * Private data structure for holding {@code Board} instance and its previous state
     */
    private class Node {
        /**
         * Current board
         */
        private final Board board;
        /**
         * {@code Node} holding {@code Board} with the previous state
         */
        private final Node parent;

        private Node(Board board) {
            this(board, null);
        }

        private Node(Board board, Node parent) {
            this.board = board;
            this.parent = parent;
        }
    }

    /**
     * Constructs the {@code Solver} and finds a solution to the initial board using the A* algorithm
     *
     * @param initial given {@code Board} for solving
     * @see Board
     * @see #trySolve(MinPQ)
     */
    public Solver(Board initial) {
        moves.insert(new Node(initial));
        twinMoves.insert(new Node(initial.twin()));
        // try to solve while one of the solutions is not found
        while (!solvable && !twinSolvable) {
            solvable = trySolve(moves);
            //there is no need for twin solution if main one is already found
            if (solvable) break;
            twinSolvable = trySolve(twinMoves);
        }
    }

    /**
     * Tries to solve the 'Puzzle Problem' using A* search algorithm
     *
     * @param moves priority queue for searching
     * @return {@code true} if {@code Board} is solved, otherwise returns {@code false}
     * @see MinPQ
     * @see #boardComparator
     * @see Board#isGoal()
     * @see Board#neighbors()
     * @see Board#equals(Object)
     * @see assignment4.Solver.Node
     */
    private boolean trySolve(MinPQ<Node> moves) {
        Node current = moves.delMin();
        if (current.board.isGoal()) {
            solved = current;
            return true;
        }
        for (Board board : current.board.neighbors()) {
            if (current.parent == null || !board.equals(current.parent.board)) {
                moves.insert(new Node(board, current));
            }
        }
        return false;
    }

    /**
     * Checks if the current {@code Board} is solvable
     *
     * @return {@code true} if {@code Board} is solvable, otherwise returns {@code false}
     */
    public boolean isSolvable() {
        return solvable;
    }

    /**
     * Returns minimal number of moves to solve initial board
     *
     * @return min number of moves or -1 if the board is not solvable
     * @see #isSolvable()
     */
    public int moves() {
        int moves = -1;
        if (isSolvable()) {
            Node current = solved;
            moves = 0;
            while ((current = current.parent) != null) {
                moves++;
            }
        }
        return moves;
    }

    /**
     * Returns sequence of boards in a shortest solution
     *
     * @return {@code Iterable<Board>} or {@code null} if the board is not solvable
     * @see Iterable
     * @see #isSolvable()
     */
    public Iterable<Board> solution() {
        LinkedList<Board> solution = null;
        if (isSolvable()) {
            Node current = solved;
            solution = new LinkedList<>();
            solution.addFirst(current.board);
            while ((current = current.parent) != null) {
                solution.addFirst(current.board);
            }
        }
        return solution;
    }

    /**
     * Creates an initial board from each filename specified
     * on the command line and finds the minimum number of moves to
     * reach the goal state.
     * <p>
     * % java PuzzleChecker puzzle*.txt
     * puzzle00.txt: 0
     * puzzle01.txt: 1
     * puzzle02.txt: 2
     * puzzle03.txt: 3
     * puzzle04.txt: 4
     * puzzle05.txt: 5
     * puzzle06.txt: 6
     * ...
     * puzzle3x3-impossible: -1
     * ...
     * puzzle42.txt: 42
     * puzzle43.txt: 43
     * puzzle44.txt: 44
     * puzzle45.txt: 45
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }


}
