package cn.cast.tree;


import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树
 */
@SuppressWarnings("unchecked")
public class BinaryTree<E> {
    protected int size; //元素数量
    protected Node<E> root; //根节点
    /**
     * 访问器接口 ——> 访问器抽象类
     * 增强遍历接口
     */
    public static abstract class Visitor<E>{
        boolean stop;
        // 如果返回true，就代表停止遍历
        public abstract boolean visit(E element);
    }

    protected static class Node<E>{
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;
        public Node(E element,Node<E> parent){
            this.element = element;
            this.parent = parent;
        }
        public boolean isLeaf(){ //是否是叶子节点
            return left == null && right == null;
        }
        public boolean hasTwoChild(){// 是否有两个子节点
            return left != null && right != null;
        }
        public boolean isLeafChild(){
            return parent != null && this == parent.left;
        }
        public boolean isRightChild(){
            return parent != null && this == parent.left;
        }
        public Node<E> sibling(){// 红黑树中用到, 返回兄弟节点
            if (isLeafChild()){
                return parent.right;
            }
            if (isRightChild()){
                return parent.left;
            }
            return null;
        }
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void clear(){
        root = null;
        size = 0;
    }
    protected Node<E> createNode(E element,Node<E> parent){
        return new Node<>(element,parent);
    }
    /**
     * 前序遍历
     */
    public void preorder(Visitor<E> visitor){
        if (visitor == null) return;
        preorder(root,visitor);
    }

    private void preorder(Node<E> root, Visitor<E> visitor) {
        if (root == null || visitor.stop) return;
        visitor.stop = visitor.visit(root.element);
        preorder(root.left,visitor);
        preorder(root.right,visitor);
    }

    public void inorder(Visitor<E> visitor){
        if (visitor==null) return;
        inorder(root,visitor);
    }

    private void inorder(Node<E> root, Visitor<E> visitor) {
        if (root == null || visitor.stop) return;
        inorder(root.left,visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(root.element);
        inorder(root.right,visitor);
    }
    public void postorder(Visitor<E> visitor) {
        if (visitor == null) return;
        postorder(root, visitor);
    }
    public void postorder(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;
        // 左
        postorder(node.left, visitor);
        // 右
        postorder(node.right, visitor);
        // 根
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
    }

    public void levelOrder(Visitor<E> visitor){
        if(root == null || visitor.stop) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node<E> poll = queue.poll();
            if (visitor.visit(poll.element)) return;

            if (root.left != null) {
                queue.offer(root.left);
            }

            if (root.right != null) {
                queue.offer(root.right);
            }
        }
    }

    /**
     * 求树的高度(递归)
     */
    public int height(){
        return height(root);
    }

    private int height(Node<E> root) {
        if (root == null) return 0;
        return 1 + Math.max(height(root.left),height(root.right));
    }

    /**
     * 求树的高度高度(迭代)
     */
    public int height1(){
        if (root == null) return 0;
        // 存储每一层的元素数量, root!=null, 则首层必然有1个元素
        int levelSize = 1;
        int height = 0; //树高
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node<E> node = queue.poll();
            levelSize--;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
            if (levelSize == 0){
                levelSize = queue.size();
                height++;
            }
        }

        return height;
    }

    /**
     * 是否是完全二叉树
     */
    public boolean isComplete(){
        boolean leaf = false; //是否是叶子节点
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node<E> node = queue.poll();
            // 要求是叶子结点，但是当前节点不是叶子结点
            if (leaf && !node.isLeaf()) return false;
            if (node.left != null) {
                queue.offer(node.left);
            }else if (node.right != null){
                return false;
            }
            if (node.right != null) {
                queue.offer(node.right);
            } else {
                leaf = true; // 要求后面都是叶子节点
            }
        }
        return true;
    }
    /**
     * 前驱节点: 中序遍历时的前一个节点
     * 求前驱节点
     */
    protected Node<E> predecessor(Node<E> node){
        if (node==null) return null;
        // 前驱节点在左子树中(left.right.right.right....)
        Node<E> p = node.left;
        if (p != null){
            while (p.right != null){
                p = p.right;
            }
            return p;
        }
        // 能来到这里说明左子树为空, 则从父节点、祖父节点中寻找前驱节点
        // 当父节点不为空, 且某节点为父节点的左子节点
        // 则顺着父节点找, 直到找到【某结点为父节点或祖父节点的右子树中】时
        while (node.parent != null && node == node.parent.left){
            node = node.parent;
        }
        return node.parent;
    }
    /**
     * 后继节点: 中序遍历时的后一个节点
     * 求后继节点
     */
    protected Node<E> successor(Node<E> node){
        if (node==null) return null;
        Node<E> p = node.right;
        if (p != null){
            while (p.left != null){
                p = p.left;
            }
            return p;
        }
        while (node.parent != null && node == node.parent.right){
            node = node.parent;
        }
        return node.parent;
    }

}
