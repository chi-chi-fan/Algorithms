public class UF{
    private int[] id;
    private int count;

    public UF(int N) {
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
}