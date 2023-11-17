import java.util.Arrays;

public class Board {
    private int[][] tiles;
    private int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = deepCopy(tiles);
        n = tiles.length;
    }

    // string representation of this board
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(tiles.length).append('\n');
        for (int[] row : tiles) {
            for (int i : row) {
                builder.append(' ').append(i);
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    // board dimension n
    public int dimension() {
        return 0;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isLastTile(i, j) && (tiles[i][j] != 0)
                        || !isLastTile(i, j) && tiles[i][j] != i * n + j + 1) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    manhattan += Math.abs((tiles[i][j] - 1) / n - i); // vertical distance
                    manhattan += Math.abs(((tiles[i][j] - 1) % n) - j); // horizontal distance
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || y.getClass() != this.getClass()) return false;
        if (((Board) y).n != this.n) return false;
        for (int i = 0; i < n; i++) {
            if (!Arrays.equals(this.tiles[i], ((Board) y).tiles[i])) return false;
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    private boolean isLastTile(int i, int j) {
        return (i == n - 1) && (j == n - 1);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {
                { 1, 0, 3 },
                { 4, 2, 5 },
                { 7, 8, 6 }
        };

        System.out.println(new Board(tiles));
    }

    private static int[][] deepCopy(int[][] tiles) {
        int[][] copy = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        return copy;
    }

}