package cn.cast.list.skiplist;

import java.util.Comparator;

/**
 * description
 *
 * @author 周德永
 * @date 2021/12/19 16:56
 */
@SuppressWarnings("unchecked")
public class SkipList<K,V> {

    private Comparator<K> comparator;

    /*参考redis 最高32层*/
    private static final int MAX_LEVEL = 32;
    private static final double P = 0.25;

    /*有效层数*/
    private int level;

    private int size;

    /*不存放任何k-v 虚拟节点*/
    private Node<K,V> first;

    public SkipList(Comparator<K> comparator){
        this.comparator = comparator;
        first = new Node<>(null,null,level);
        first.nexts = new Node[MAX_LEVEL];

    };

    public SkipList() {
        this(null);
    }

    public int size(){
        return size;
    }
    public boolean isEmpty(){
        return size == 0;
    }
    public V put(K key,V value){
        keyCheck(key);
        Node<K, V> node = this.first;
        Node<K, V>[] prev = new Node[level];
        for (int i = level-1; i >= 0 ; i--) {
            int cmp = -1;
            while (node.nexts[i] != null && (cmp = cmp(key,node.nexts[i].key)) > 0){
                node = node.nexts[i];
            }
            if (cmp == 0){
                V oldV = node.nexts[i].value;
                node.nexts[i].value = value;
                return oldV;
            }
            prev[i] = node;
        }
        /*添加新节点*/
        int randomLevel = randomLevel();
        Node<K, V> kvNode = new Node<>(key, value,randomLevel);
        for (int i = 0; i < randomLevel; i++) {
            if (i >= level){
                first.nexts[i] = kvNode;
            }else {
                kvNode.nexts[i] = prev[i].nexts[i];
                prev[i].nexts[i] = kvNode;
            }
        }

        size++;
        level = Math.max(level,randomLevel);
        return null;
    }
    private int randomLevel() {
        int level = 1;
        while (Math.random() < P && level < MAX_LEVEL){
            level++;
        }
        return level;
    }
    public V get(K key){
        keyCheck(key);
        Node<K, V> node = this.first;
        for (int i = level-1; i >= 0 ; i--) {
            int cmp = -1;
            while (node.nexts[i] != null && (cmp = cmp(key,node.nexts[i].key)) > 0){
                node = node.nexts[i];
            }
            /*node.nexts[i].key >= key*/
            if (cmp == 0) return node.nexts[i].value;

        }
        return null;
    }
    public V remove(K key){
        keyCheck(key);
        Node<K, V> node = this.first;
        Node<K, V>[] prevs = new Node[level];
        boolean exist = false;
        for (int i = level - 1; i >= 0 ; i--) {
            int cmp = -1;
            while (node.nexts[i] != null && (cmp = cmp(key,node.nexts[i].key)) > 0){
                node = node.nexts[i];
            }
            if (cmp == 0) exist = true;
            prevs[i] = node;
        }
        /*跳表中没有该元素*/
        if (!exist) return null;
        /*需要被删除的结点*/
        Node<K,V> removeNode = node.nexts[0];
        for (int i = 0; i < removeNode.nexts.length; i++) {
            prevs[i].nexts[i] = removeNode.nexts[i];
        }
        /*更新跳表的层数*/
        int newLevel = level;
        while (--newLevel >= 0 && first.nexts[newLevel] == null){
            level = newLevel;
        }

        size--;

        return removeNode.value;
    }

    private void keyCheck(K key){
        if (key==null){
            throw new IllegalArgumentException("key must not be null.");
        }
    }

    private int cmp(K k1,K k2){
        return comparator!=null ? comparator.compare(k1, k2) : ((Comparable<K>)k1).compareTo(k2);
    }

    private static class Node<K,V>{
        K key;
        V value;
        Node<K,V>[] nexts;

        public Node(K key, V value,int level) {
            this.key = key;
            this.value = value;
            nexts = new Node[level];
        }
    }
}
