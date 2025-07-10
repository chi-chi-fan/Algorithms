import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation { 
    private int n; // Size of the grid
    private int[][] grid; // 2D array to represent the grid
    private int virtualTop; // Virtual top site
    private int virtualBottom; // Virtual bottom site
    private WeightedQuickUnionUF uf; // Union-Find data structure for connectivity
    private WeightedQuickUnionUF ufFull; // Union-Find for full sites (to avoid backwash)

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) { 
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }
        this.n = n;
        this.grid = new int[n][n]; // Initialize the grid
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                // Initialize the grid with blocked sites
                grid[i][j] = 0; // 0 represents blocked site
            }
        }
        int totalSites = n * n;
        virtualTop = totalSites; // Virtual top site index
        virtualBottom = totalSites + 1; // Virtual bottom site index
        uf = new WeightedQuickUnionUF(totalSites + 2); // Union-Find for percolation
        ufFull = new WeightedQuickUnionUF(totalSites + 1); // Union-Find for full sites (without virtual bottom)
        // Connect virtual top to all sites in the first row
        for (int col = 0; col < n; col++) {
            uf.union(virtualTop, getIndex(0, col));
            ufFull.union(virtualTop, getIndex(0, col)); 
        }     
    }

    private int getIndex(int row, int col) {
        return row * n + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if(row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException("Row and column indices must be within grid bounds");
        }
        if (!isOpen(row, col)) {
            grid[row][col] = 1; // Mark the site as open
            // Connect to adjacent open sites (if any)
            connectToAdjacent(row, col);
        }
    }

    private void connectToAdjacent(int row, int col) {
        int index = getIndex(row, col);

        // Connect to virtual top if in first row
        if (row == 0) {
            uf.union(index, virtualTop);
            ufFull.union(index, virtualTop);
        }

        // Connect to virtual bottom if in last row (only in uf)
        if (row == n - 1) {
            uf.union(index, virtualBottom);
        }

        // Up
        if (row > 0 && isOpen(row - 1, col)) {
            int above = getIndex(row - 1, col);
            uf.union(index, above);
            ufFull.union(index, above);
        }
        // Down
        if (row < n - 1 && isOpen(row + 1, col)) {
            int below = getIndex(row + 1, col);
            uf.union(index, below);
            ufFull.union(index, below);
        }
        // Left
        if (col > 0 && isOpen(row, col - 1)) {
            int left = getIndex(row, col - 1);
            uf.union(index, left);
            ufFull.union(index, left);
        }
        // Right
        if (col < n - 1 && isOpen(row, col + 1)) {
            int right = getIndex(row, col + 1);
            uf.union(index, right);
            ufFull.union(index, right);
        }
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if(row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException("Row and column indices must be within grid bounds");
        }
        return grid[row][col] == 1; // 1 represents open site
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

    }

    // returns the number of open sites
    public int numberOfOpenSites() {

    }

    // does the system percolate?
    public boolean percolates() {

    }

    // test client (optional)
    public static void main(String[] args) {

    }

}