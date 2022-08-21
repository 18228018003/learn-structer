package com.zdy.learn.tree;

/**
 * description
 *
 * @author 周德永
 * @date 2022/3/15 23:51
 */
public interface Trie <V> {
    int size();
    boolean isEmpty();
    void clear();
    boolean contains(String str);
    V add(String str,V value);
    V remove(String str);
    boolean starswith(String prefix);
}
