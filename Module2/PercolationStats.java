import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] thresholds; // Array to store percolation thresholds
    private final int trials; // Number of trials

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be > 0");
        }

        this.trials = trials;
        thresholds = new double[trials];

        for (int t = 0; t < trials; t++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1); // 1-based indexing
                int col = StdRandom.uniformInt(1, n + 1); // 1-based indexing
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
            }
            double threshold = (double) p.numberOfOpenSites() / (n * n);
            thresholds[t] = threshold;
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
        return mean() - (1.96 * stddev() / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Provide n and trials as command-line arguments");
        }

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.printf("mean                    = %.16f\n", stats.mean());
        StdOut.printf("stddev                  = %.16f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = [%.16f, %.16f]\n",
                      stats.confidenceLo(), stats.confidenceHi());
    }
}