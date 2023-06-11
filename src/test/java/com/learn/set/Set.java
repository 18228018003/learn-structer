package com.learn.set;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/28 20:48
 */
public interface Set<E> {
    int size();
    boolean isEmpty();
    void clear();
    boolean contains(E element);
    void add(E element);
    void remove(E element);
    void traversal(cn.cast.list.map.set.Set.Visitor<E> visitor);

    public static abstract class Visitor<E> {
        boolean stop;
        public abstract boolean visit(E element);
    }

    public static void main(String[] args) {
        System.out.println(65536 << 2);
    }
}

