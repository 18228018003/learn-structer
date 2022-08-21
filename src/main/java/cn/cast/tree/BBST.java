package cn.cast.tree;

import java.util.Comparator;

public class BBST<E> extends BST<E> {
    public BBST(){
        this(null);
    }
    public BBST(Comparator<E> comparator){
        super(comparator);
    }
    /**
     * 右旋转
     */
    protected void rotateRight(Node<E> grand){
        Node<E> parent = grand.left;
        Node<E> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(parent, grand, child);
    }
    /**
     * RR左旋转
     */
    protected void rotateLeft(Node<E> grand){
        Node<E> parent = grand.right;
        Node<E> child = parent.left; //将子节点存储起来
        grand.right = child;
        parent.left = grand;
        afterRotate(parent, grand, child);
    }
    /**
     * 统一处理的旋转代码（神奇）
     */
    protected void rotate(
            Node<E> r, // 子树的根节点
            Node<E> b, Node<E> c,
            Node<E> d,
            Node<E> e, Node<E> f) {
        // 让d成为这棵子树的根节点
        d.parent = r.parent;
        if (r.isLeafChild()) {
            r.parent.left = d;
        } else if (r.isRightChild()) {
            r.parent.right = d;
        } else {
            root = d;
        }

        //b-c
        b.right = c;
        if (c != null) {
            c.parent = b;
        }

        // e-f
        f.left = e;
        if (e != null) {
            e.parent = f;
        }

        // b-d-f
        d.left = b;
        d.right = f;
        b.parent = d;
        f.parent = d;
    }
    protected void afterRotate(Node<E> parent, Node<E> grand, Node<E> child) {
        parent.parent = grand.parent;//设置parent父节点
        if (grand.isLeafChild()){
            grand.parent.left = parent;
        }else if (grand.isRightChild()){
            grand.parent.right = parent;
        }else {
            root = parent;
        }
        /*child节点的父节点*/
        if (child != null){
            child.parent = grand;
        }
        /*grand节点的父节点*/
        grand.parent = parent;

    }

}
