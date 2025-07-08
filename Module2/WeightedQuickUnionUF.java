public class WeightedQuickUnionUF {
    int[] id; // Array to hold the component identifiers
    int[] size; // Array to hold the size of each component
    int count; // Number of components  

    public WeightedQuickUnionUF(int N) {
        count = N;
        id = new int[N];
        size = new int[N]; // size of components for roots
        for (int i = 0; i < N; i++) {
            id[i] = i; // Initialize each element to point to itself
            size[i] = 1; // Initialize the size of each component to 1
        }
    }
    public int count() {
        return count; // Return the number of components
    }
    public boolean connected(int p, int q) {
        return find(p) == find(q); // Check if two elements are in the same component
    }
    public int find(int p) {
        while (p != id[p]) {
            p = id[p]; // Follow the chain of roots until we reach the root 
        }
        return p; // Return the root of the component containing p
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return; // They are already connected

        // Union by size: attach smaller tree under larger tree
        if (size[rootP] < size[rootQ]) {
            id[rootP] = rootQ; // Connect rootP to rootQ
            size[rootQ] += size[rootP]; // Update the size of the new root
        } else {
            id[rootQ] = rootP; // Connect rootQ to rootP
            size[rootP] += size[rootQ]; // Update the size of the new root
        }
        count--; // Decrease the number of components
    }
}
