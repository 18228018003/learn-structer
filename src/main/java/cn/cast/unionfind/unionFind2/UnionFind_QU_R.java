package cn.cast.unionfind.unionFind2;

public class UnionFind_QU_R extends QuickUnion{
    private int[] rank;
    public UnionFind_QU_R(int capacity) {
        super(capacity);
        rank = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            rank[i]  = i;
        }
    }

    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (rank[p1] < rank[p2]){
            parents[p1] = p2;
        }else if (rank[p1] > rank[p2]){
            parents[p2] = p1;
        }else {
            parents[p1] = p2;
            rank[p2] += 1;
        }
    }
}
