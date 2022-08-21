package cn.cast.unionfind;

import cn.cast.graph.UnionFind;
import cn.cast.list.Asserts;
import cn.cast.list.map.Times;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/30 22:23
 */
public class Main {
    static final int count = 1000000;
    public static void main(String[] args) {
//        test(new UnionFind_QU(12));
//        test(new UnionFind_QF(12));
//        test(new UnionFind_QU_S(12));

//        testTime(new UnionFind_QU_RANK(count));
//        testTime(new UnionFind_QF(count));
//        GenericUnionFind<Student> uf = new GenericUnionFind<>();
//        Student zs = new Student(14, "zs");
//        Student ls = new Student(12, "ls");
//        uf.union(zs, ls);
//        uf.same(zs,ls);
        test(new UnionFind<Integer>());
//        testTime(new UnionFind_QU_RAND_PC(count));
//        testTime(new UnionFind_QU_RANK_PS(count));
    }

    static void test(UnionFind<Integer> uf){
        for (int i = 0; i < count; i++) {
            uf.makeSet(i);
        }
        uf.union(0,1);
        uf.union(0,3);
        uf.union(0,4);
        uf.union(2,3);
        uf.union(2,5);

        uf.union(6,7);
        uf.union(8,10);
        uf.union(9,10);
        uf.union(9,11);

        Asserts.test(!uf.isSame(2,7));
        uf.union(4,6);
        Asserts.test(uf.isSame(2,7));
        Times.test(uf.getClass().getSimpleName(),()->{
            for (int i = 0; i < count; i++) {
                uf.union((int)(Math.random()*count),(int)(Math.random()*count));
            }
            for (int i = 0; i < count; i++) {
                uf.isSame((int)(Math.random()*count),(int)(Math.random()*count));
            }
        });
    }

    static void testTime(cn.cast.unionfind.UnionFind uf){
        Times.test(uf.getClass().getSimpleName(),()->{
            for (int i = 0; i < count; i++) {
                uf.union((int)(Math.random()*count),(int)(Math.random()*count));
            }
            for (int i = 0; i < count; i++) {
                uf.isSame((int)(Math.random()*count),(int)(Math.random()*count));
            }
        });
    }


}
