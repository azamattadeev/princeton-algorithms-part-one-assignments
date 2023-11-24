/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class BoardUnitTest {
    // hamming = 3, manhattan = 3
    private static final int[][] NOT_GOAL_1 = {
            { 1, 0, 3 },
            { 4, 2, 5 },
            { 7, 8, 6 }
    };
    // hamming = 5, manhattan = 10
    private static final int[][] NOT_GOAL_2 = {
            { 8, 1, 3 },
            { 4, 0, 2 },
            { 7, 6, 5 }
    };
    // hamming, manhattan = 0;
    private static final int[][] GOAL = {
            { 1, 2, 3 },
            { 4, 5, 6 },
            { 7, 8, 0 }
    };

    private static final int[][][] NOT_GOAL_1_NEIGHBORS = {
            {
                    { 0, 1, 3 },
                    { 4, 2, 5 },
                    { 7, 8, 6 }
            },

            {
                    { 1, 2, 3 },
                    { 4, 0, 5 },
                    { 7, 8, 6 }
            },

            {
                    { 1, 3, 0 },
                    { 4, 2, 5 },
                    { 7, 8, 6 }
            }
    };

    private static final int[][][] NOT_GOAL_2_NEIGHBORS = {
            {
                    { 8, 0, 3 },
                    { 4, 1, 2 },
                    { 7, 6, 5 }
            },

            {
                    { 8, 1, 3 },
                    { 0, 4, 2 },
                    { 7, 6, 5 }
            },

            {
                    { 8, 1, 3 },
                    { 4, 2, 0 },
                    { 7, 6, 5 }
            },

            {
                    { 8, 1, 3 },
                    { 4, 6, 2 },
                    { 7, 0, 5 }
            }
    };

    private static final int[][][] GOAL_NEIGHBORS = {
            {
                    { 1, 2, 3 },
                    { 4, 5, 0 },
                    { 7, 8, 6 }
            },

            {
                    { 1, 2, 3 },
                    { 4, 5, 6 },
                    { 7, 0, 8 }
            }
    };

    @Test
    public void hammingTest() {
        assertEquals(3, new Board(NOT_GOAL_1).hamming());
    }

    @Test
    public void hammingGoalTest() {
        assertEquals(0, new Board(GOAL).hamming());
    }

    @Test
    public void manhattanNotGoalTest1() {
        assertEquals(3, new Board(NOT_GOAL_1).manhattan());
    }

    @Test
    public void manhattanNotGoalTest2() {
        assertEquals(10, new Board(NOT_GOAL_2).manhattan());
    }

    @Test
    public void manhattanGoalTest() {
        assertEquals(0, new Board(GOAL).manhattan());
    }

    @Test
    public void equalsTest() {
        assertEquals(new Board(NOT_GOAL_1), new Board(NOT_GOAL_1));
    }

    @Test
    public void notEqualsTest() {
        assertNotEquals(new Board(NOT_GOAL_1), new Board(NOT_GOAL_2));
    }

    @Test
    public void equalsNullTest() {
        assertNotEquals(new Board(GOAL), null);
    }

    @Test
    public void threeNeighborsTest() {
        neighborTests(NOT_GOAL_1_NEIGHBORS, NOT_GOAL_1);
    }

    @Test
    public void fourNeighborsTest() {
        neighborTests(NOT_GOAL_2_NEIGHBORS, NOT_GOAL_2);
    }

    @Test
    public void twoNeighborsTest() {
        neighborTests(GOAL_NEIGHBORS, GOAL);
    }

    private void neighborTests(int[][][] expectedNeighborsArray, int[][] tiles) {
        Iterable<Board> neighborsIterable = new Board(tiles).neighbors();
        List<Board> neighborsList = new ArrayList<>(4);
        neighborsIterable.forEach(neighborsList::add);

        assertEquals(expectedNeighborsArray.length, neighborsList.size());

        for (int[][] neighborTiles : expectedNeighborsArray) {
            assertTrue(neighborsList.contains(new Board(neighborTiles)));
        }
    }

    @Test
    public void twinTest() {
        Board goalBoard = new Board(GOAL);
        assertEquals(2, goalBoard.twin().hamming());
    }

}
