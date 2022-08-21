package cn.cast.map;


public class LinkedHashMap<K,V> extends HashMap<K,V>{
    private static class LinkedNode<K,V> extends Node<K,V>{

        public LinkedNode(K key, V value, Node<K, V> parent) {
            super(key, value, parent);
        }
    }

}
