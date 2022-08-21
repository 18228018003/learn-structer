package cn.cast.tree;

import java.util.Comparator;

public class AVLTree<E> extends BBST<E> {
    public AVLTree(Comparator<E> comparator){
        super(comparator);
    }
    public AVLTree(){
        this(null);
    }
    // AVL树的节点，需要计算平衡因子，因此比普通二叉树多维护一个height属性(将height放入普通二叉树里没有用处，浪费空间)
    private static class AVLNode<E> extends Node<E>{
        int height = 1;
        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }
        public int balanceFactor(){
            // 获取该节点平衡因子(左子树高度 - 右子树高度)
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = left == null ? 0 : ((AVLNode<E>)right).height;
            return leftHeight - rightHeight;
        }
        public void updateHeight(){// 更新高度
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            height = 1 + Math.max(leftHeight,rightHeight);
        }
        public Node<E> tallerChild(){
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            if (leftHeight > rightHeight) return left;
            if (rightHeight > leftHeight) return right;
            // 高度一样则返回同方向的，左子节点则返回左，否则返回右
            return isLeafChild() ? left : right;
        }
        @Override
        public String toString() {
            String parentString = "null";
            if (parent != null) {
                parentString = parent.element.toString();
            }
            return element + "_p(" + parentString + ")_h(" + height + ")";
        }
    }
    @Override
    public Node<E> createNode(E element,Node<E> parent){
        return new AVLNode<>(element,parent);
    }
    /**
     * 判断传入节点是否平衡（平衡因子的绝对值 <= 1）
     */
    public boolean isBalanced(Node<E> node){
        return Math.abs(((AVLNode<E>)node).balanceFactor()) <= 1;
    }
    /**
     * 更新高度
     */
    public void updateHeight(Node<E> node){
        ((AVLNode<E>) node).updateHeight();
    }


    @Override
    protected void afterRemove(Node<E> node) {
        while ((node = node.parent) != null){
            if (isBalanced(node)){
                updateHeight(node);
            }else {
                rebalance(node);
            }
        }
    }

    /**
     * 增加节点后的调整
     */
    @Override
    protected void afterAdd(Node<E> node) {
        while ((node = node.parent) != null){
            if (isBalanced(node)){/*是否平衡*/
                //更新高度
                updateHeight(node);
            }else {
                //恢复平衡
                rebalance(node);
                // 只要恢复了最下面的子树的平衡, 则整棵树恢复平衡
                break;
            }
        }
    }
    /**
     * 恢复平衡
     * @param grand 高度最低的那个不平衡节点
     */
    private void rebalance(Node<E> grand) {
        Node<E> parent = ((AVLNode<E>) grand).tallerChild();
        Node<E> node = ((AVLNode<E>) parent).tallerChild();
        if (parent.isLeafChild()){ //L
            if (node.isLeafChild()){ //LL
                rotateRight(grand);
            }else { //LR
                rotateLeft(parent);
                rotateRight(grand);
            }
        }else { //R
            if (node.isRightChild()) { //RR
                rotateLeft(grand);
            }else {
                rotateRight(parent);
                rotateLeft(grand);
            }
        }
    }
    @Override
    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        super.afterRotate(grand, parent, child);

        // 更新高度
        updateHeight(grand);
        updateHeight(parent);
    }

    @Override
    protected void rotate(Node<E> r, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f) {
        super.rotate(r, b, c, d, e, f);
        // 更新高度
        updateHeight(b);
        updateHeight(f);
        updateHeight(d);
    }
}
