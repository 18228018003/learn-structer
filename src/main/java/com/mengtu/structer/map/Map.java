package com.mengtu.structer.map;

public interface Map<K,V> {
    int size();
    boolean isEmpty();
    V put(K key,V value);
    V get(K key);
    V remove(K key);
    void clear();
    boolean containsKey(K key);
    boolean containsValue(V value);
    void traversal(Visitor<K,V> visitor);

    abstract class Visitor<K,V> {
        boolean stop;
        abstract boolean visit(K key,V value);
    }
}
