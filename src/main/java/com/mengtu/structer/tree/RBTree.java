package com.mengtu.structer.tree;

import java.util.Comparator;

public class RBTree<E> extends BBST<E> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public RBTree(){
        this(null);
    }

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     * 红黑树的节点类
     * 节点有颜色，RED 或者 BLACK
     */
    private static class RBNode<E> extends Node<E>{
        boolean color = RED;
        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED) {
                str = "R_";
            }
            return str + element.toString();
        }
    }

    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;
        if (parent == null){
            black(node);
            return;
        }
        if (isBlack(parent)) return;
        Node<E> uncle = parent.sibling();
        Node<E> grand = red(parent.parent);
        if (isRed(uncle)){
            black(parent);
            black(uncle);
            afterAdd(grand);
            return;
        }
        if (parent.isLeftChild()){ //L
            if (node.isLeftChild()){//LL
                black(parent);
            }else { //LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(parent);
        }else { //R
            if (node.isRightChild()){//RR
                black(parent);
            }else {//RL
                black(node);
                rotateRight(parent);
            }
            rotateLeft(grand);
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        // 如果删除的节点是红色
        // 或者 用以取代删除节点的子节点是红色
        if (isRed(node)){
            black(node);
            return;
        }
        Node<E> parent = node.parent;
        // 删除的是根节点
        if (parent == null) return;
        // 删除的是黑色叶子节点【下溢】
        // 判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right : parent.left;
        if (left) { //被删除的节点在左边 兄弟节点在右边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        }else {//被删除的节点在右边 兄弟节点在左边
            if (isRed(sibling)){
                black(sibling);
                red(parent);
                rotateRight(parent);
                //更换兄弟
                sibling = parent.left;
            }
            //兄弟节点是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)){//无红色子节点
                //父节点跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack){
                    afterRemove(parent);
                }
            }else {//兄弟至少有一个红色子节点
                if (isBlack(sibling.left)){
                    rotateLeft(sibling);
                    sibling = parent.left;
                }
                color(sibling,colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }

    /**
     * 下面是一些辅助方法
     */
    private Node<E> color(Node<E> node,boolean color){
        if (node == null) return null;
        ((RBNode<E>)node).color = color;
        return node;
    }
    // 将该节点染为红色
    private Node<E> red(Node<E> node){
        return color(node,RED);
    }
    // 将该节点染为黑色
    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }
    // 返回该节点的颜色
    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : ((RBNode<E>)node).color;
    }
    // 该节点是否为黑色
    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }
    // 该节点是否为红色
    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }
    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent);
    }
}
