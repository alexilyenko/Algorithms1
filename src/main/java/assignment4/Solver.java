package assignment4;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

/**
 * Created by oilyenko on 5/29/2015.
 */
public class Solver {
    public Solver(Board initial) {
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

    public String moves() {
        return null;
    }

    public boolean isSolvable() {
        return false;
    }

    public Board[] solution() {
        return null;
    }
}
