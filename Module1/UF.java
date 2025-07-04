public class UF{
    private int[] id;
    private int count;

    public UF(int N) {
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i; // Initialize each element to point to itself
            // labels are group ID
            // values in the array are not important, only care about the component labels
        }
    }
    public int count() {
        return count; // Return the number of components
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q); // Check if two elements are in the same component
    }
    public int find(int p) {
        return id[p]; // Find the root of the component containing p
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return; // They are already connected

        for (int i = 0; i < id.length; i++) {
            if (id[i] == rootP) {
                id[i] = rootQ; // Connect the two components
            }
        }
        count--; // Decrease the number of components
    }
    public void union_root(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return; // They are already connected

        id[rootP] = rootQ; // Connect the root of p to the root of q
        // This is a more efficient way to connect components
        // as it avoids iterating through the entire array.
        // It assumes that the roots are unique and that we are only connecting roots.
        // This is a common optimization in union-find algorithms.
        // It reduces the time complexity of union operations.
        // The union operation now only updates the root of one component to point to the root of
        // another component, rather than iterating through all elements.
        // This is more efficient, especially for large datasets.
        // The union operation now has a time complexity of O(1) for the root connection
        // and O(N) for the initial setup, but the overall performance is improved
        // because we are not iterating through the entire array for every union operation.
        count--; // Decrease the number of components
    }
}