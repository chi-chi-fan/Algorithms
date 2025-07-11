public class QuickUnionUF {
    private int[] id; // id[i] is the parent of i
    private int count; // number of components

    public QuickUnionUF(int N) {
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i; // Initialize each element to point to itself
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

        id[rootP] = rootQ; // Connect the root of p to the root of q
        count--; // Decrease the number of components
    }
}