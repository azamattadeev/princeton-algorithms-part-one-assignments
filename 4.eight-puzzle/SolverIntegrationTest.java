/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SolverIntegrationTest {

    // 3 steps to goal
    private static final int[][] NOT_GOAL_3x3 = {
            { 1, 0, 3 },
            { 4, 2, 5 },
            { 7, 8, 6 }
    };

    // 21 steps to goal
    private static final int[][] NOT_GOAL_4x4 = {
            { 5, 1, 11, 3 },
            { 0, 2, 7, 4 },
            { 13, 9, 12, 8 },
            { 10, 14, 6, 15 }
    };

    private static final int[][] NOT_GOAL_4x4_UNSOLVABLE = {
            { 5, 1, 7, 3 },
            { 0, 2, 11, 4 },
            { 13, 9, 12, 8 },
            { 10, 14, 6, 15 }
    };

    private static final int[][] GOAL3x3 = {
            { 1, 2, 3 },
            { 4, 5, 6 },
            { 7, 8, 0 }
    };

    private static final int[][] GOAL4x4 = {
            { 1, 2, 3, 4 },
            { 5, 6, 7, 8 },
            { 9, 10, 11, 12 },
            { 13, 14, 15, 0 }
    };

    @Test
    public void test3x3() {
        Solver solver = new Solver(new Board(NOT_GOAL_3x3));
        assertEquals(3, solver.moves());

        List<Board> path = ((List<Board>) solver.solution());

        assertEquals(new Board(GOAL3x3), path.get(path.size() - 1));
        assertEquals(1, path.get(path.size() - 2).manhattan());
    }

    @Test
    public void test4x4() {
        Solver solver = new Solver(new Board(NOT_GOAL_4x4));
        assertEquals(21, solver.moves());

        List<Board> path = ((List<Board>) solver.solution());

        assertEquals(new Board(GOAL4x4), path.get(path.size() - 1));
        assertEquals(1, path.get(path.size() - 2).manhattan());
    }

    @Test
    public void testUnsolvable4x4() {
        Solver solver = new Solver(new Board(NOT_GOAL_4x4_UNSOLVABLE));
        assertEquals(-1, solver.moves());
    }

    @Test
    public void testGoal() {
        Solver solver = new Solver(new Board(GOAL3x3));
        assertEquals(0, solver.moves());

        List<Board> path = ((List<Board>) solver.solution());

        assertEquals(new Board(GOAL3x3), path.get(path.size() - 1));
    }

}
