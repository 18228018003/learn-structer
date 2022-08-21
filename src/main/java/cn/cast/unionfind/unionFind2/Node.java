package cn.cast.unionfind.unionFind2;

public class Node<V> {
    V value;
    Node<V> parent;
    int rank = 1;
    Node(V value) {
        this.value = value;
    }
}
