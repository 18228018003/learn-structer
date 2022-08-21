package cn.cast.unionfind;

/**
 *  基于rank 路劲压缩
 * @author 周德永
 * @date 2021/12/1 0:01
 */
public class UnionFind_QU_RAND_PC extends UnionFind_QU_RANK{

    public UnionFind_QU_RAND_PC(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        if (v != parents[v]){
            parents[v] = find(parents[v]);
        }
        return parents[v];
    }
}
