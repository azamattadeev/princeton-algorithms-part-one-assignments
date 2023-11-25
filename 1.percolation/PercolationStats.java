import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private double[] thresholds;
    private final int t;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        thresholds = new double[trials];
        t = trials;

        for (int i = 0; i < trials; i++) {
            var percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniformInt(n) + 1, StdRandom.uniformInt(n) + 1);
            }
            thresholds[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(t);
    }

    // test client (see below)
    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        var percolationStats =
                new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        StdOut.printf("%-23s = %f\n", "mean", percolationStats.mean());
        StdOut.printf("%-23s = %f\n", "stddev", percolationStats.stddev());
        StdOut.printf("%-23s = [%f, %f]\n",
                      "95% confidence interval",
                      percolationStats.confidenceLo(),
                      percolationStats.confidenceHi());
        System.out.printf("Time: %s sec\n", stopwatch.elapsedTime());
    }

}