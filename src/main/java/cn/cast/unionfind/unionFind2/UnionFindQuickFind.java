package cn.cast.unionfind.unionFind2;

public class UnionFindQuickFind extends UnionFind{
    public UnionFindQuickFind(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        return parents[v];
    }

    /**
     * 将v1所在集合的所有元素都嫁接到v2的父节点上
     * v1    v2   union(v1,v2)
     *  0    4	     4
     * 1 2   3     0 1 2 3
     */
    public void union(int v1, int v2){
        int p1 = parents[v1];
        int p2 = parents[v2];

        for (int i = 0; i < parents.length; i++) {
            if (parents[i] == p1){
                parents[i] = p2;
            }
        }
    }
}
