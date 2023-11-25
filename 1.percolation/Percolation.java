/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int openCount = 0;

    private WeightedQuickUnionUF weightedQuickUnionUF;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException();
        grid = new boolean[n][n]; // all sites are blocked by default
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2); // +2 for virtual sites
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateCoords(row, col);
        if (grid[row - 1][col - 1]) return;

        grid[row - 1][col - 1] = true;
        openCount++;

        if (col > 1 && grid[row - 1][col - 2]) {
            weightedQuickUnionUF.union(coordsToIndex(row, col), coordsToIndex(row, col - 1));
        }
        if (col < grid.length && grid[row - 1][col]) {
            weightedQuickUnionUF.union(coordsToIndex(row, col), coordsToIndex(row, col + 1));
        }
        if (row > 1 && grid[row - 2][col - 1]) {
            weightedQuickUnionUF.union(coordsToIndex(row, col), coordsToIndex(row - 1, col));
        }
        if (row < grid.length && grid[row][col - 1]) {
            weightedQuickUnionUF.union(coordsToIndex(row, col), coordsToIndex(row + 1, col));
        }
        if (row == 1) {
            weightedQuickUnionUF.union(coordsToIndex(row, col), getVirtualTopIndex());
        }
        if (row == grid.length) {
            weightedQuickUnionUF.union(coordsToIndex(row, col), getVirtualBottomIndex());
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateCoords(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateCoords(row, col);
        return weightedQuickUnionUF.find(getVirtualTopIndex())
                == weightedQuickUnionUF.find(coordsToIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.find(getVirtualTopIndex())
                == weightedQuickUnionUF.find(getVirtualBottomIndex());
    }

    public static void main(String[] args) {
        // var percolation = new Percolation(4);
        // int[][] coords = {
        //         { 3, 1 },
        //         { 2, 2 },
        //         { 3, 3 },
        //         { 2, 4 },
        //         { 1, 3 },
        //         { 1, 1 },
        //         { 4, 2 },
        //         { 4, 4 },
        //         { 2, 3 },
        //         { 4, 3 },
        //         };
        //
        // for (int[] coord : coords) {
        //     percolation.open(coord[0], coord[1]);
        //     System.out.println(percolation.percolates());
        // }

    }

    private int getVirtualTopIndex() {
        return grid.length * grid.length;
    }

    private int getVirtualBottomIndex() {
        return grid.length * grid.length + 1;
    }

    private int coordsToIndex(int row, int col) {
        return (row - 1) * grid.length + col - 1;
    }

    private void validateCoords(int row, int col) {
        if (Math.max(row, col) > grid.length || Math.min(row, col) < 1) {
            throw new IllegalArgumentException();
        }
    }

}
