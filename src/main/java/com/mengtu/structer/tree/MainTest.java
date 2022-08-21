package com.mengtu.structer.tree;

public class MainTest {
    public static void main(String[] args) {
        MyBinarySearchTree<Integer> tree = new MyBinarySearchTree<>();
        tree.add(4);
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(5);
        tree.add(6);
        System.out.println(tree.height());
        System.out.println(tree.heightLevelOrder());
    }
}
