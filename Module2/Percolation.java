import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final boolean[][] grid;
    private final int virtualTop;
    private final int virtualBottom;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufFull;
    private int openCount;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }

        this.n = n;
        this.grid = new boolean[n][n];
        this.openCount = 0;
        int totalSites = n * n;
        virtualTop = totalSites;
        virtualBottom = totalSites + 1;
        uf = new WeightedQuickUnionUF(totalSites + 2);
        ufFull = new WeightedQuickUnionUF(totalSites + 1);

        // Connect virtual top to all top-row sites
        for (int col = 1; col <= n; col++) {
            int index = getIndex(1, col);
            uf.union(virtualTop, index);
            ufFull.union(virtualTop, index);
        }
    }

    // validate row and col indices (1-based)
    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("row or col out of bounds");
        }
    }

    // convert 1-based (row, col) to 0-based array index
    private int getIndex(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        int r = row - 1;
        int c = col - 1;
        if (!grid[r][c]) {
            grid[r][c] = true;
            openCount++;
            connectToAdjacent(row, col);
        }
    }

    // connect open site at (row, col) to its open neighbors
    private void connectToAdjacent(int row, int col) {
        int index = getIndex(row, col);

        // Connect to virtual bottom if in last row
        if (row == n) {
            uf.union(index, virtualBottom);
        }

        // Up
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(index, getIndex(row - 1, col));
            ufFull.union(index, getIndex(row - 1, col));
        }

        // Down
        if (row < n && isOpen(row + 1, col)) {
            uf.union(index, getIndex(row + 1, col));
            ufFull.union(index, getIndex(row + 1, col));
        }

        // Left
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(index, getIndex(row, col - 1));
            ufFull.union(index, getIndex(row, col - 1));
        }

        // Right
        if (col < n && isOpen(row, col + 1)) {
            uf.union(index, getIndex(row, col + 1));
            ufFull.union(index, getIndex(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) return false;
        int index = getIndex(row, col);
        return ufFull.connected(index, virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(virtualTop, virtualBottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(1, 2);
        p.open(2, 2);
        p.open(3, 2);

        System.out.println("Percolates? " + p.percolates());          // true
        System.out.println("Is (3, 3) full? " + p.isFull(3, 3));       // false
        System.out.println("Number of open sites: " + p.numberOfOpenSites()); // 3
    }
}