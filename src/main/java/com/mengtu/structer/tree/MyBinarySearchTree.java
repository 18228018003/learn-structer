package com.mengtu.structer.tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class MyBinarySearchTree<E> {

    private int size;
    private Node<E> root;
    private Comparator<E> comparator;

    public MyBinarySearchTree() {
        this(null);
    }

    public MyBinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
    }

    public void add(E element) {
        elementNotNullCheck(element);
        if (root == null) {
            root = new Node<>(element, null);
            size++;
            return;
        }
        Node<E> node = root;
        Node<E> parent = null;
        int cmp = 0;
        while (node != null) {
            cmp = compare(element, node.element);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                //do nothing
                return;
            }
        }
        //插入到父节点的具体位置
        Node<E> newNode = new Node<>(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
    }

    /**
     * 1:e1 > e2; 0:e1==e2  -1:e1 < e2
     */
    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<E>) e1).compareTo(e2);
    }

    public void preorderTraversal() {
        preorderTraversal(root);
    }

    private void preorderTraversal(Node<E> node) {
        if (node == null) return;
        System.out.println(node.element);
        preorderTraversal(node.left);
        preorderTraversal(node.right);
    }

    public void inorderTraversal() {
        inorderTraversal(root);
    }

    private void inorderTraversal(Node<E> node) {
        if (node == null) return;
        inorderTraversal(node.left);
        System.out.println(node.element);
        inorderTraversal(node.right);
    }

    public void postorderTraversal() {
        postorderTraversal(root);
    }

    private void postorderTraversal(Node<E> node) {
        if (node == null) return;
        postorderTraversal(node.left);
        postorderTraversal(node.right);
        System.out.println(node.element);
    }

    public void levelOrderTraversal(Visitor<E> visitor) {
        if (root == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (visitor.visit(node.element)) return;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    public int height() {
        return height(root);
    }

    public int heightLevelOrder() {
        if (root == null) return 0;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        int height = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node<E> node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            height++;
        }
        return height;
    }

    /* public boolean isComplete(){
         if (root == null) return true;
         Queue<Node<E>> queue = new LinkedList<>();
         queue.offer(root);
         boolean leaf = false;
         while (!queue.isEmpty()){
             Node<E> node = queue.poll();
             if (leaf && !node.isLeaf()){
                 return false;
             }
             if (node.hasTwoChildren()){ //左右子树都不为空
                 queue.offer(node.left);
                 queue.offer(node.right);
             }else if (node.left == null && node.right != null){ //左子树为空 右子树不为空
                 return false;
             }else{ //后面遍历的都必须是叶子节点
                 leaf = true;
                 if (node.left != null){
                     queue.offer(node.left);
                 }
             }
         }
         return true;
     }*/
    public boolean isComplete() {
        if (root == null) return true;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean leaf = false;
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (leaf && !node.isLeaf()) {
                return false;
            }
            if (node.left != null) {
                queue.offer(node.left);
            } else if (node.right != null) { //node.left == null node.right != null
                return false;
            }

            if (node.right != null) {
                queue.offer(node.right);
            } else {
                leaf = true;
            }
        }
        return true;
    }

    private int height(Node<E> node) {
        if (node == null) return 0;
        return Math.max(height(node.right), height(node.left)) + 1;
    }

    public void remove(E element) {
        remove(node(element));
    }
    private void remove(Node<E> node){
        if (node == null) return;
        size--;
        if (node.hasTwoChildren()){
            //度为2
            Node<E> s = successor(node);
            node.element = s.element;
            node = s;
        }
        //删除node节点 node的度必然是1或者0
        Node<E> replacement = node.left != null ? node.left:node.right;
        if (replacement != null){//度为1
            //更改parent
            replacement.parent = node.parent;
            if (node.parent == null){
                root = replacement;
            } else if (node.parent.left == node){
                node.parent.left = replacement;
            }else if (node.parent.right == node){
                node.parent.right = replacement;
            }
        }else if (node.parent == null){ //node 是叶子节点并且是根节点
            root = null;
        }else {
            if (node == node.parent.left){
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
        }

    }
    private Node<E> node(E element){
        Node<E> node = root;
        while (node != null){
            int cmp = compare(element,node.element);
            if (cmp > 0){
                node = node.right;
            }else if (cmp < 0){
                node = node.left;
            }else{
                return node;
            }
        }
        return null;
    }
    public boolean contains(E element) {
        return false;
    }

    private Node<E> predecessor(Node<E> node) {
        if (node == null) return null;
        //前驱节点在左子树中
        Node<E> p = node.left;
        if (node.left != null){
            while (p.right != null){
                p = p.right;
            }
            return p;
        }
        //从祖父节点中寻找前驱节点
        while (node.parent != null && node.parent.left == node){
            node = node.parent;
        }

        return node.parent;
    }
    private Node<E> successor(Node<E> node) {
        if (node == null) return null;
        //后继节点在右子树中
        Node<E> p = node.right;
        if (node.right != null){
            while (p.left != null){
                p = p.left;
            }
            return p;
        }
        //从祖父节点中寻找前驱节点
        while (node.parent != null && node.parent.right == node){
            node = node.parent;
        }

        return node.parent;
    }
    private void elementNotNullCheck(E element) {
        if (element == null)
            throw new IllegalArgumentException("element must not be null");
    }

    public abstract class Visitor<E> {
        boolean stop;

        //如果返回true 停止遍历
        abstract boolean visit(E element);
    }

    private static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }
    }


}
