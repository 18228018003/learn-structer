package com.mengtu.structer.tree;

import java.util.Comparator;

/**
 * 二叉搜索树
 */
@SuppressWarnings("unchecked")
public class BST<E> extends BinaryTree<E> {
    private final Comparator<E> comparator;

    public BST(Comparator<E> comparator){
        this.comparator = comparator;
    }

    public BST(){
        this(null);
    }
    // 根据元素值获取节点元素
    private Node<E> node(E element){
        elementNotNullCheck(element);
        Node<E> node = root;
        while (node != null){
            int cmp = compareTo(element,node.element);
            if (cmp < 0) {
                node = node.left;
            }else if (cmp > 0){
                node = node.right;
            }else {
                return node;
            }
        }
        return null;
    }
    /**
     * 是否包含某元素
     */
    public boolean contains(E element){
        return node(element) != null;
    }

    public void add(E element){
        elementNotNullCheck(element);
        // 传入第一个节点, 若根节点为null, 则该节点为根节点
        if (root == null){
            root = new Node<>(element,null);
            size++;
            afterAdd(root);
            return;
        }
        // 添加的不是第一个节点, 找到父节点
        Node<E> node = root;
        Node<E> parent = root;
        int cmp = 0;
        while (node != null){
            parent = node;// 父节点
            // 比较【传入节点的元素值】与【父节点的元素值】
            cmp = compareTo(element,node.element);
            if (cmp > 0){
                node = node.right;
            }else if (cmp < 0){
                node = node.left;
            }else {// 相等, 最好是覆盖掉, 也可以采取其他操作, 看业务需求
                node.element = element;
            }
        }
        Node<E> newNode = createNode(element,parent);
        if (cmp > 0){
            parent.right = newNode;
        }else {
            parent.left = newNode;
        }
        afterAdd(newNode);
        size++;
    }
    public void remove(E element){
        remove(node(element));
    }

    protected void afterAdd(Node<E> node){}

    protected void afterRemove(Node<E> node,Node<E> replacement){}

    private void remove(Node<E> node) {
        if (node == null) return;
        size--;
        if (node.hasTwoChild()){
            // 找到后继节点
            Node<E> s = successor(node);
            // 用后继节点的值覆盖度为2的节点的值
            node.element = s.element;
            // 删除后继节点
            node = s;
        }
        // 删除node节点（node的度必然是1或者0）
        Node<E> replacement = node.left != null ? node.left:node.right;
        if (replacement != null){// node是度为1的节点
            // 更改parent
            replacement.parent = node.parent;
            if (node.parent == null){
                root = replacement;
            }else if (node == node.parent.left){
                node.parent.left = replacement;
            }else {
                node.parent.right = replacement;
            }
            afterRemove(node,replacement);
        }else if (node.parent == null){// node是叶子节点并且是根节点
            root = null;
        }else {// node是叶子节点，但不是根节点
            if (node == node.parent.left){
                node.parent.left = null;
            }else {
                node.parent.right = null;
            }
            afterRemove(node,null );
        }

    }


    private void elementNotNullCheck(E element) {
        if (element == null) { // 不能传入空节点
            throw new IllegalArgumentException("element must not null");
        }
    }
    // 节点元素比较
    private int compareTo(E e1,E e2){
        if (comparator != null){
            return comparator.compare(e1,e2);
        }
        // 没传比较器，元素内部必须自行实现了 Comparable 接口
        return ((Comparable<E>)e1).compareTo(e2);
    }
}
