import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.List;

/*
    A* algorithm modification for 8 puzzle problem
 */
public class Solver {
    private Iterable<Board> solution;
    private int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Argument is null");

        MinPQ<SearchNode> mainQueue = new MinPQ<>();
        MinPQ<SearchNode> twinQueue = new MinPQ<>();

        mainQueue.insert(new SearchNode(initial, 0, null));
        twinQueue.insert(new SearchNode(initial.twin(), 0, null));

        SearchNode goalNode = null;

        while (step(twinQueue) == null && goalNode == null) {
            goalNode = step(mainQueue);
        }

        if (goalNode != null) {
            moves = goalNode.moves;
            solution = buildPath(goalNode);
        }
        else {
            moves = -1;
            solution = null;
        }
    }

    private SearchNode step(MinPQ<SearchNode> queue) {
        SearchNode current = queue.delMin();

        if (current.board.isGoal()) {
            return current;
        }

        for (Board neighborBoard : current.board.neighbors()) {
            if (current.previous == null || !current.previous.board.equals(neighborBoard)) {
                queue.insert(new SearchNode(neighborBoard, current.moves + 1, current));
            }
        }

        return null;
    }

    private List<Board> buildPath(SearchNode goal) {
        SearchNode current = goal;
        SearchNode previous = null;

        while (current != null) {
            SearchNode nextCurr = current.previous;
            current.previous = previous;
            previous = current;
            current = nextCurr;
        }

        List<Board> path = new ArrayList<>(moves + 1);

        while (previous != null) {
            path.add(previous.board);
            previous = previous.previous;
        }
        return path;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    private static class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private int manhattan;
        private SearchNode previous;

        SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.manhattan = board.manhattan();
        }

        public int compareTo(SearchNode node) {
            return Integer.compare(this.manhattan + moves,
                                   node.manhattan + node.moves);
        }
    }

}