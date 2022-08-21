package com.mengtu.structer.tree;

import java.util.HashMap;

public class Trie<V> {
    private Node<V> root = new Node<>();
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        root.getChildren().clear();
    }

    public boolean contains(String str) {
        return node(str) != null;
    }

    public V add(String str, V value) {
        keyCheck(str);
        int len = str.length();
        Node<V> node = root;
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            Node<V> childNode = root.getChildren().get(c);
            if (childNode == null) {
                childNode = new Node<>();
                node.getChildren().put(c, childNode);
            }
            node = childNode;
        }
        if (node.word) {
            V old = node.value;
            node.value = value;
            return old;
        }

        node.word = true;
        node.value = value;
        size++;
        return null;
    }

    private Node<V> node(String key) {
        keyCheck(key);
        Node<V> node = root;
        int len = key.length();
        for (int i = 0; i < len; i++) {
            char c = key.charAt(i);
            node = node.getChildren().get(c);
            if (node == null) return null;
        }

        return node.word ? node : null;
    }

    private void keyCheck(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("key must not be empty");
        }
    }

    public V remove(String str, V value) {
        
        return null;
    }

    public boolean startWith(String prefix) {
        keyCheck(prefix);
        Node<V> node = this.root;
        int len = prefix.length();
        for (int i = 0; i < len; i++) {
            char c = prefix.charAt(i);
            node = node.getChildren().get(c);
            if (node == null) return false;
        }
        return true;
    }

    private static class Node<V> {
        HashMap<Character, Node<V>> children;
        V value;
        boolean word;

        public HashMap<Character, Node<V>> getChildren() {
            return children == null ? (children = new HashMap<>()) : children;
        }
    }

    public V get(String key) {
        Node<V> node = node(key);
        return node == null ? null : node.value;
    }
}
