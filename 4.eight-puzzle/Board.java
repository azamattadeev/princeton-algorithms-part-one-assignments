import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private int[][] tiles;
    private int n;
    private int zY; // y coordinate of 0 tile
    private int zX; // x coordinate of 0 tile

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = deepCopy(tiles);
        n = tiles.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] == 0) {
                    zY = i;
                    zX = j;
                }
            }
        }
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
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!isLastTile(i, j) && tiles[i][j] != 0 && tiles[i][j] != i * n + j + 1
                        || isLastTile(i, j) && (tiles[i][j] != 0)) {
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
        return (hamming() == 0);
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
        List<Board> neighbors = new ArrayList<>(4);
        List<int[]> slideCoords = new ArrayList<>(4);

        if (zY > 0) slideCoords.add(new int[] { zY - 1, zX });
        if (zY < n - 1) slideCoords.add(new int[] { zY + 1, zX });
        if (zX > 0) slideCoords.add(new int[] { zY, zX - 1 });
        if (zX < n - 1) slideCoords.add(new int[] { zY, zX + 1 });

        for (int[] slideFrom : slideCoords) {

            // Swap two tiles in original tiles[][] array
            // It's need to avoid using extra memory because Board() constructor creates copy of argument array.
            swapTiles(zY, zX, slideFrom[0], slideFrom[1]);

            // create neighbor Board
            neighbors.add(new Board(tiles));

            // swap back in original tiles[][] array
            swapTiles(zY, zX, slideFrom[0], slideFrom[1]);
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twin;
        if (tiles[0][0] == 0) {
            swapTiles(0, 1, 1, 0);
            twin = new Board(tiles);
            swapTiles(0, 1, 1, 0);
        }
        else if (tiles[0][1] == 0) {
            swapTiles(0, 0, 1, 0);
            twin = new Board(tiles);
            swapTiles(0, 0, 1, 0);
        }
        else {
            swapTiles(0, 0, 0, 1);
            twin = new Board(tiles);
            swapTiles(0, 0, 0, 1);
        }

        return twin;
    }

    private void swapTiles(int y1, int x1, int y2, int x2) {
        int p = tiles[y1][x1];
        tiles[y1][x1] = tiles[y2][x2];
        tiles[y2][x2] = p;
    }

    private boolean isLastTile(int i, int j) {
        return (i == n - 1) && (j == n - 1);
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