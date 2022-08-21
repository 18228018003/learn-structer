package com.zdy.learn.tree;

import java.util.*;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/20 20:52
 */
public class BinarySearchTree<E>{
    /*数大小*/
    private int size;
    /*根节点*/
    private Node<E> root;

    private Comparator<E> comparator;

    public BinarySearchTree(){
        this(null);
    }
    public BinarySearchTree(Comparator<E> comparator){
        this.comparator = comparator;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void clear(){

    }

    public void add(E element){
        elementNotNullCheck(element);
        /*添加第一个节点*/
        if (root == null){
            root = new Node<>(element, null);
            size++;
            return;
        }
        /*添加的不是第一个根节点*/
        /*找到父节点*/
        Node<E> parent = null;
        Node<E> node = root;
        int i = 0;
        while (node != null){
            i = compare(element, node.element);
            parent = node;
            if (i > 0){
                node = node.right;
            }else if (i < 0){
                node = node.left;
            }else {
                return;
            }
        }
        /*看插入到父节点的什么位置*/
        Node<E> eNode = new Node<>(element, parent);
        if (i > 0){
            parent.right = eNode;
        }else {
            parent.left = eNode;
        }
        size++;
    }

    /**
     * @return 返回值等于0 相等，大于0 e1>e2 小于0 e1<e2;
     */
    private int compare(E e1,E e2){
        if (this.comparator != null){
            comparator.compare(e1,e2);
        }
        return ((Comparable<E>)e1).compareTo(e2);
    }
    public void remove(E element){
        remove(node(element));
    }

    private void remove(Node<E> node) {
        if (node == null) return;
        size--;
        if (node.hasTwoChildren()){/*度为2*/
            Node<E> sNode = successor(node);/*找到后继节点*/
            /*用后继节点的值覆盖度为2的节点的值*/
            node.element=sNode.element;
            /*删除后继节点*/
            node = sNode;
        }
        /*删除node node的度必然是1或者0*/
        /*如果是叶子节点 replacement一定为空*/
        Node<E> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) {/*node是度为1的节点*/
            /*更改parent*/
            replacement.parent = node.parent;
            if (node.parent == null) {/*node是度为1的节点 并且是根节点*/
                root = replacement;
            } else if (node == node.parent.left){
                node.parent.left = replacement;
            } else if (node == node.parent.right){
                node.parent.right = replacement;
            }
        }else if (node.parent == null){/*node是叶子节点并且是根节点*/
            root = null;
        }else {/*node是叶子节点但不是根节点*/
            if (node == node.parent.right){
                node.parent.right = null;
            }
            if (node == node.parent.left){
                node.parent.left = null;
            }
        }

    }

    private Node<E> node(E element) {
        Node<E> node = root;
        while (node != null){
            int cmp = compare(element,node.element);
            if (cmp > 0){
                node = node.right;
            }else if (cmp < 0){
                node = node.left;
            }else {
                return node;
            }
        }
        return null;
    }

    private void elementNotNullCheck(E element){
        if (element == null){
            throw new IllegalArgumentException("element must not be null");
        }
    }

    private static class Node<E>{
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;
        public Node(E element,Node<E> parent){
            this.element = element;
            this.parent = parent;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }
    }

    public void preOrder(Visitor<E> visitor){
        Stack<Node<E>> stack = new Stack<>();
        Node<E> node = root;
        stack.push(node);
        while (!stack.isEmpty()){
            Node<E> cur = stack.pop();
            if (visitor.visit(cur.element)) {
                return;
            }

            if (cur.right != null){
                stack.push(cur.right);
            }
            if (cur.left != null){
                stack.push(cur.left);
            }
        }
    }
    public void inOrder(Node<E> node){
        if (node != null){
            Stack<Node<E>> stack = new Stack<>();
            while (!stack.isEmpty() || node != null){
                if (node != null){
                    stack.push(node);
                    node = node.left;
                }else {
                    node = stack.pop();
                    System.out.println(node.element);
                    node = node.right;
                }
            }
        }
    }

    public void levelOrder(Visitor<E> visitor){
        if (root == null || visitor == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node<E> cur = queue.poll();
            if (visitor.visit(cur.element)) return;

            if (cur.left != null){
                queue.offer(cur.left);
            }
            if (cur.right != null){
                queue.offer(cur.right);
            }
        }
    }

    public static abstract class Visitor<E>{
        boolean stop;
        abstract boolean visit(E element);
    }

    public int getHeight(){
        if (root == null) return 0;
        int height = 0;
        /*存储每一层的元素数量*/
        int levelSize = 1;
        Node<E> node = root;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()){
            node = queue.poll();
            levelSize--;

            if (node.left!=null){
                queue.offer(node.left);
            }
            if (node.right!=null){
                queue.offer(node.right);
            }
            if (levelSize == 0){/*意味着即将访问下一层*/
                levelSize = queue.size();
                height++;
            }
        }
        return height;
    }

    public int height(){
        return height(root);
    }

    private int height(Node<E> node) {
        if (node == null) return 0;
        return 1+Math.max(height(node.left),height(node.right));
    }

    public int getMaxWeight(){
        Node<E> node = root;
        if (node == null) return 0;
        HashMap<Node<E>, Integer> levelMap = new HashMap<>();
        levelMap.put(node,1);
        int max = -1;
        int curLevel = 1;
        int curLevelNodes = 0;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()){
            node = queue.poll();
            Integer curNodeLevel = levelMap.get(node);
            if (curNodeLevel == curLevel){
                curLevelNodes++;
            }else {
                max = Math.max(max,curLevelNodes);
                curLevel++;
                curLevelNodes = 1;
            }

            if (node.left!=null){
                levelMap.put(node.left,curNodeLevel+1);
                queue.offer(node.left);
            }
            if (node.right!=null){
                levelMap.put(node.right,curNodeLevel+1);
                queue.offer(node.right);
            }
        }
        return max;
    }

    public boolean isComplete(){
        Node<E> node = root;
        if (node == null) return true;
        Queue<Node<E>> queue = new LinkedList<>();
        Node<E> left;
        Node<E> right;
        boolean isLeaf = false;

        while (!queue.isEmpty()){
            node = queue.poll();
            left = node.left;
            right = node.right;
            if ((isLeaf && (left != null || right!=null)) || (left==null && right!=null)) return false;
            if (left != null){
                queue.offer(left);
            }
            if (right != null){
                queue.offer(right);
            }
            if (left == null || right == null){
                isLeaf = true;
            }
        }
        return true;
    }

    private Node<E> predecessor(Node<E> node){
        if (node == null) return null;
        /*前驱节点在左子树*/
        if (node.left != null) {
            Node<E> p = node.left;
            while (p.right != null){
                p = p.right;
            }
            return p;
        }
        /*从父节点，祖父节点中寻找前驱节点*/
        while (node.parent != null && node == node.parent.left){
            node = node.parent;
        }
        return node.parent;
    }
    private Node<E> successor(Node<E> node){
        if (node == null) return null;
        /*后继节点在右子树*/
        Node<E> p = node.right;
        if (p != null){
            while (p.left != null){
                p = p.left;
            }
            return p;
        }
        /*从父节点，祖父节点中寻找后继节点*/
        while (node.parent != null && node == node.parent.right){
            node = node.parent;
        }
        return node.parent;
    }

}
