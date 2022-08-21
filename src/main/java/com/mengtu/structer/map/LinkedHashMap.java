package com.mengtu.structer.map;


public class LinkedHashMap<K,V> extends HashMap<K,V> {

    private LinkedNode<K,V> first;
    private LinkedNode<K,V> last;

    private static class LinkedNode<K,V> extends Node<K,V>{
        LinkedNode<K,V> prev;
        LinkedNode<K,V> next;

        public LinkedNode(K key, V value, Node<K, V> parent) {
            super(key, value, parent);
        }
    }

    @Override
    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        LinkedNode<K, V> node = new LinkedNode<>(key, value, parent);
        if (first == null){
            first = last = node;
        }else {
            last.next = node;
            node.prev = last;
            last = node;
        }
        return node;
    }

    @Override
    public void clear() {
        super.clear();
        first = null;
        last = null;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) return;
        LinkedNode<K, V> node = this.first;
        while (node != null){
            if (visitor.visit(node.key, node.value)) return;
            node = node.next;
        }
    }

    @Override
    protected void afterRemove(Node<K,V> willNode,Node<K, V> removeNode) {
        LinkedNode<K,V> linkedWillNode = (LinkedNode<K, V>) willNode;
        LinkedNode<K,V> linkedRemoveNode = (LinkedNode<K, V>) removeNode;
        if (linkedRemoveNode != linkedWillNode) {
            //交换linkedRemoveNode和linkedWillNode
            LinkedNode<K,V> temp = linkedWillNode.prev;
            linkedWillNode.prev = linkedRemoveNode.prev;
            linkedRemoveNode.prev = temp;
            if (linkedWillNode.prev == null){
                first = linkedWillNode;
            }else {
                linkedWillNode.prev.next = linkedWillNode;
            }
            if (linkedRemoveNode.prev == null){
                first = linkedRemoveNode;
            }else {
                linkedRemoveNode.prev.next = linkedRemoveNode;
            }
        }
        if (removeNode == null ) return;

        LinkedNode<K, V> prev = linkedRemoveNode.prev;
        LinkedNode<K, V> next = linkedRemoveNode.next;
        if (prev == null){
            first = next;
        }else {
            prev.next = next;
        }

        if (next == null){
            last = prev;
        }else {
            last.prev = prev;
        }
    }
}
