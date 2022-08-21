package com.learn.map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/28 20:49
 */
public class TreeMap<K,V> implements Map<K,V>{
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size;
    private Node<K, V> root;
    private Comparator<K> comparator;

    public TreeMap(){
        this(null);
    }
    public TreeMap(Comparator<K> comparator){
        this.comparator = comparator;
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

    private Node<K,V> successor(Node<K, V> node){
        if (node == null) return null;
        // 后驱节点在右子树当中（right.left.left.left....）
        Node<K, V> p = node.right;
        if (p != null){
            while (p.left != null){
                p = p.left;
            }
            return p;
        }
        // 从父节点、祖父节点中寻找后继节点
        while (node.parent != null && node==node.parent.right){
            node = node.parent;
        }
        return node.parent;
    }
    private Node<K, V> predecessor(Node<K, V> node) {
        // 前驱节点在左子树当中（left.right.right.right....）
        Node<K, V> p = node.left;
        if (p != null){
            while (p.right != null){
                p = p.right;
            }
            return p;
        }
        // 从父节点、祖父节点中寻找前驱节点
        while (node.parent != null && node == node.parent.left){
            node = node.parent;
        }
        return node.parent;
    }
    @Override
    public V put(K key, V value) {
        keyNotNullCheck(key);
        // 添加第一个节点
        if (root == null){
            root = new Node<>(key,value,null);
            size++;
            // 新添加节点之后的处理
            afterPut(root);
            return null;
        }
        // 添加的不是第一个节点
        // 找到父节点
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        do {
            cmp = compare(key, node.key);
            parent = node;
            if (cmp > 0){
                node = node.right;
            }else if (cmp < 0){
                node = node.left;
            }else {/*相等*/
                node.key = key;
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        } while (node != null);
        // 看看插入到父节点的哪个位置
        Node<K, V> kvNode = new Node<>(key, value, parent);
        if (cmp > 0){
            parent.right = kvNode;
        }else {
            parent.left = kvNode;
        }
        size++;
        afterPut(kvNode);
        return null;
    }

    private void afterPut(Node<K,V> node) {
        Node<K, V> parent = node.parent;

        // 添加的是根节点 或者 上溢到达了根节点
        if (parent == null) {
            black(node);
            return;
        }
        // 如果父节点是黑色，直接返回
        if (isBlack(parent)) return;
        // 叔父节点
        Node<K, V> sibling = parent.sibling();
        // 祖父节点
        Node<K, V> grand = red(parent.parent);
        if (isRed(sibling)){// 叔父节点是红色【B树节点上溢】
            black(sibling);
            black(parent);
            // 把祖父节点当做是新添加的节点
            afterPut(grand);
            return;
        }
        // 叔父节点不是红色
        if (parent.isLeftChild()){//l
            if(node.isLeftChild()){//ll
                black(parent);
            }else {//lr
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        }else {// R
            if (node.isLeftChild()) { // RL
                black(node);
                rotateRight(parent);
            }else {//RR
                black(parent);
            }
            rotateLeft(grand);
        }
    }

    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }
    private void rotateRight(Node<K,V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand,parent,child);
    }
    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
        parent.parent = grand.parent;
        if (grand.isLeftChild()){
            grand.parent.left = parent;
        }else if (grand.isRightChild()){
            grand.parent.right = parent;
        }else {
            root = parent;// grand是root节点
        }
        if (child != null){
            child.parent = grand;
        }
        // 更新grand的parent
        grand.parent = parent;
    }



    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node != null ? node.value : null;
    }

    private Node<K,V> node(K key) {
        Node<K, V> node = this.root;
        while (node != null){
            int cmp = compare(key,node.key);
            if (cmp == 0) return node;
            if (cmp > 0){
                node = node.right;
            }else {
                node = node.left;
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }
    private V remove(Node<K, V> node) {
        if (node == null) return null;
        size--;
        V oldValue = node.value;
        if (node.hasTwoChildren()) { // 度为2的节点
            Node<K, V> s = successor(node);
            node.key = s.key;
            node.value = s.value;
            node = s;
        }
        // 删除node节点（node的度必然是1或者0）
        Node<K,V> replacement = node.left != null?node.left : node.right;
        if (replacement != null){// node是度为1的节点
            replacement.parent = node.parent;
            if (node.parent == null){// node是度为1的节点并且是根节点
                root = replacement;
            }else if (node == node.parent.left){
                node.parent.left= replacement;
            }else { // node == node.parent.right
                node.parent.right = replacement;
            }
            afterRemove(replacement);
        }else if (node.parent == null){
            root = null;
        }else {
            if (node == node.parent.left){
                node.parent.left= null;
            }else {
                node.parent.right = null;
            }
            afterRemove(node);
        }
        return oldValue;
    }

    private void afterRemove(Node<K, V> node) {
        // 如果删除的节点是红色
        // 或者 用以取代删除节点的子节点是红色
        if (isRed(node)){
            black(node);
            return;
        }
        Node<K, V> parent = node.parent;
        if (parent==null) return;

        // 删除的是黑色叶子节点【下溢】
        // 判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<K,V> sibling = left?parent.right:parent.left;
        if (left){// 被删除的节点在左边，兄弟节点在右边

        }else {// 被删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)){
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }
            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack){
                    afterRemove(parent);
                }
            }else {
                // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
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

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (root == null) return false;
        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<K, V> node = queue.poll();
            if (valEquals(value, node.value)) return true;
            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return false;
    }

    @Override
    public void traversal(cn.cast.list.map.map.Map.Visitor<K, V> visitor) {

    }

    private boolean valEquals(V v1, V v2) {
        return v1 == null ? v2 == null : v1.equals(v2);
    }








    private Node<K,V> color(Node<K,V> node,boolean color){
        if(node == null) return null;
        node.color = color;
        return node;
    }
    private Node<K,V> red(Node<K,V> node){
       return color(node,RED);
    }
    private Node<K,V> black(Node<K,V> node){
        return color(node,BLACK);
    }
    private boolean colorOf(Node<K,V> node){
        return node == null?BLACK:node.color;
    }
    private boolean isBlack(Node<K,V> node){
        return colorOf(node) == BLACK;
    }
    private boolean isRed(Node<K,V> node){
        return colorOf(node) == RED;
    }
    private int compare(K k1,K k2){
        if (comparator!=null){
           return comparator.compare(k1, k2);
        }
        return ((Comparable<K>)k1).compareTo(k2);
    }
    private void keyNotNullCheck(K key){
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
    }
    private static class Node<K, V> {
        K key;
        V value;
        boolean color = RED;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;
        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
        public boolean isLeaf(){
            return left==null&&right==null;
        }
        public boolean hasTwoChildren(){
            return left!=null && right!=null;
        }
        public boolean isLeftChild(){
            return this.parent !=null && this == parent.left;
        }
        public boolean isRightChild(){
            return this.parent !=null && this == parent.right;
        }
        public Node<K,V> sibling(){
            if (isLeftChild()){
                return right;
            }
            if (isRightChild()){
                return left;
            }
            return null;
        }
    }
}