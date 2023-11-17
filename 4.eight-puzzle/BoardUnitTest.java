/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import org.junit.Assert;
import org.junit.Test;

public class BoardUnitTest {
    // hamming = 4, manhattan = 3
    private static final int[][] TILES_3_3_NOT_GOAL = {
            { 1, 0, 3 },
            { 4, 2, 5 },
            { 7, 8, 6 }
    };
    // hamming = 5, manhattan = 10
    private static final int[][] TILES_3_3_NOT_GOAL_2 = {
            { 8, 1, 3 },
            { 4, 0, 2 },
            { 7, 6, 5 }
    };
    // hamming, manhattan = 0;
    private static final int[][] TILES_3_3_GOAL = {
            { 1, 2, 3 },
            { 4, 5, 6 },
            { 7, 8, 0 }
    };

    // public static void main(String[] args) {
    //     hammingTest();
    //     hammingGoalTest();
    //     manhattanNotGoalTest1();
    //     manhattanNotGoalTest2();
    //     manhattanGoalTest();
    //     equalsTest();
    //     notEqualsTest();
    //     equalsNullTest();
    // }

    @Test
    public void hammingTest() {
        Assert.assertEquals(4, new Board(deepCopy(TILES_3_3_NOT_GOAL)).hamming());
    }

    @Test
    public void hammingGoalTest() {
        Assert.assertEquals(0, new Board(deepCopy(TILES_3_3_GOAL)).hamming());
    }

    @Test
    public void manhattanNotGoalTest1() {
        Assert.assertEquals(3, new Board(deepCopy(TILES_3_3_NOT_GOAL)).manhattan());
    }

    @Test
    public void manhattanNotGoalTest2() {
        Assert.assertEquals(10, new Board(deepCopy(TILES_3_3_NOT_GOAL_2)).manhattan());
    }

    @Test
    public void manhattanGoalTest() {
        Assert.assertEquals(0, new Board(deepCopy(TILES_3_3_GOAL)).manhattan());
    }

    @Test
    public void equalsTest() {
        Assert.assertEquals(new Board(TILES_3_3_NOT_GOAL), new Board(TILES_3_3_NOT_GOAL));
    }

    @Test
    public void notEqualsTest() {
        Assert.assertNotEquals(new Board(TILES_3_3_NOT_GOAL), new Board(TILES_3_3_NOT_GOAL_2));
    }

    @Test
    public void equalsNullTest() {
        Assert.assertNotEquals(new Board(TILES_3_3_GOAL), null);
    }

    private int[][] deepCopy(int[][] tiles) {
        int[][] copy = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        return copy;
    }

}
