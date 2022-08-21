package cn.cast.unionfind;

/**
 *   路劲压缩
 * @author 周德永
 * @date 2021/12/1 21:30
 */
public class UnionFind_QU_RANK_PS extends UnionFind_QU_RANK{

    public UnionFind_QU_RANK_PS(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        while (v!=parents[v]){
            int p = parents[v];
            parents[v] = parents[parents[v]];
            v = p;
        }
        return v;
    }
}
