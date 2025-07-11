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
        int pid = find(p);
        int qid = find(q);
        if (pid == qid) return; // They are already connected

        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = qid; // Connect all elements in the component of p to the component of q
            }
        }
        count--; // Decrease the number of components
    }

}