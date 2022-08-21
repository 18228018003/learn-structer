package com.mengtu.structer.map;


import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class TreeMap<K,V> implements Map<K,V> {

   /* private static class KV<K,V>{
        K key;
        V value;
    }

    private RBTree<KV> tree = new RBTree<>();*/
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private int size; //元素数量
    private Node<K,V> root; //根节点
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
    private static class Node<K,V>{
        boolean color = RED;
        K key;
        V value;
        Node<K,V> left;
        Node<K,V> right;
        Node<K,V> parent;
        public Node(K key,V value, Node<K,V> parent){
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
        public boolean isLeaf(){ //是否是叶子节点
            return left == null && right == null;
        }
        public boolean hasTwoChild(){// 是否有两个子节点
            return left != null && right != null;
        }
        public boolean isLeftChild(){
            return parent != null && this == parent.left;
        }
        public boolean isRightChild(){
            return parent != null && this == parent.left;
        }
        public Node<K,V> sibling(){// 红黑树中用到, 返回兄弟节点
            if (isLeftChild()){
                return parent.right;
            }
            if (isRightChild()){
                return parent.left;
            }
            return null;
        }
   }

   @Override
    public V put(K key,V value){
        keyNotNullCheck(key);
        // 传入第一个节点, 若根节点为null, 则该节点为根节点
        if (root == null){
            root = new Node<>(key,value,null);
            size++;
            afterPut(root);
            return value;
        }
        // 添加的不是第一个节点, 找到父节点
        Node<K,V> node = root;
        Node<K,V> parent = root;
        int cmp = 0;
        while (node != null){
            parent = node;// 父节点
            // 比较【传入节点的元素值】与【父节点的元素值】
            cmp = compareTo(key,node.key);
            if (cmp > 0){
                node = node.right;
            }else if (cmp < 0){
                node = node.left;
            }else {// 相等, 最好是覆盖掉, 也可以采取其他操作, 看业务需求
                node.key = key;
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        }
        Node<K,V> newNode = new Node<>(key,value,parent);
        if (cmp > 0){
            parent.right = newNode;
        }else {
            parent.left = newNode;
        }
        afterPut(newNode);
        size++;
        return null;
    }
    private void keyNotNullCheck(K key) {
        if (key == null) { // 不能传入空节点
            throw new IllegalArgumentException("key must not null");
        }
    }
    private void afterPut(Node<K,V> node){
        Node<K,V> parent = node.parent;
        if (parent == null){
            black(node);
            return;
        }
        if (isBlack(parent)) return;
        Node<K,V> uncle = parent.sibling();
        Node<K,V> grand = red(parent.parent);
        if (isRed(uncle)){
            black(parent);
            black(uncle);
            afterPut(grand);
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
    /**
     * 右旋转
     */
    private void rotateRight(Node<K,V> grand){
        Node<K,V> parent = grand.left;
        Node<K,V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(parent, grand, child);
    }
    /**
     * RR左旋转
     */
    private void rotateLeft(Node<K,V> grand){
        Node<K,V> parent = grand.right;
        Node<K,V> child = parent.left; //将子节点存储起来
        grand.right = child;
        parent.left = grand;
        afterRotate(parent, grand, child);
    }
    private void afterRotate(Node<K,V> parent, Node<K,V> grand, Node<K,V> child) {
        parent.parent = grand.parent;//设置parent父节点
        if (grand.isLeftChild()){
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
    // 节点元素比较
    private int compareTo(K e1,K e2){
        if (comparator != null){
            return comparator.compare(e1,e2);
        }
        // 没传比较器，元素内部必须自行实现了 Comparable 接口
        return ((Comparable<K>)e1).compareTo(e2);
    }
    // 根据元素值获取节点元素
    private Node<K, V> node(K key){
        keyNotNullCheck(key);
        Node<K, V> node = root;
        while (node != null){
            int cmp = compareTo(key,node.key);
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
    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node != null ? node.value : null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }
    protected Node<K, V> predecessor(Node<K, V> node) {
        if (node == null) return null;

        // 前驱节点在左子树当中（left.right.right.right....）
        Node<K, V> p = node.left;
        if (p != null) {
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }

        // 从父节点、祖父节点中寻找前驱节点
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }

        // node.parent == null
        // node == node.parent.right
        return node.parent;
    }
    private Node<K,V> successor(Node<K,V> node) {
        if (node == null) return null;
        // 后继节点在左子树的右子树中（node.left.right.right...）
        Node<K,V> p = node.right;
        if (p != null){
            while (p.left != null){
                p = p.left;
            }
            return p;
        }
        // 从父节点、祖父节点中寻找后继节点
        while (node.parent != null && node == node.parent.right){
            node = node.parent;
        }
        return node.parent;
    }

    private V remove(Node<K, V> node) {
        if (node == null) return null;
        Node<K,V> oldNode = node;
        size--;

        if (node.hasTwoChild()){ //度为2的节点
            // 找到后继节点
            Node<K,V> s = successor(node);
            node.key = s.key;
            node.value = s.value;
            node = s;
        }
        // 删除node节点（node的度必然是1或者0）
        Node<K,V> replacement = node.left != null ? node.left : node.right;
        if (replacement != null){
            // 核心：用子节点替代原节点的位置
            // 更改parent
            replacement.parent = node.parent;
            if (node.parent == null){ // node是度为1的节点并且是根节点
                root = replacement;
            }else if (node.parent.left == node){
                node.parent.left = replacement;
            }else {
                node.parent.right = replacement;
            }
            // 删除节点之后的处理，BST中无需处理，为 AVL树 和 B树提供可覆盖的方法
            afterRemove(replacement);
        }else if (node.parent == null){ // node是叶子节点并且是根节点
            root = null;
            // 删除节点之后的处理，BST中无需处理，为 AVL树 和 B树提供可覆盖的方法
            afterRemove(node);
        }else {
            if (node.parent.left == node){
                node.parent.left = null;
            }else {
                node.parent.right = null;
            }
            // 删除节点之后的处理，BST中无需处理，为 AVL树 和 B树提供可覆盖的方法
            afterRemove(node);
        }
        return oldNode.value;
    }

    private void afterRemove(Node<K,V> node) {
        // 如果删除的节点是红色
        // 或者 用以取代删除节点的子节点是红色
        if (isRed(node)) {
            black(node);
            return;
        }
        Node<K, V> parent = node.parent;
        // 删除的是根节点
        if (parent == null) return;
        // 删除的是黑色叶子节点【下溢】
        // 判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<K,V> sibling = left ? parent.right:parent.left;
        if (left){
            // 被删除的节点在左边，兄弟节点在右边
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
        }else {// 被删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)){// 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                sibling = parent.left;
            }
            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)){
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack){
                    afterRemove(parent);
                }
            }else {
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.left)){
                    rotateLeft(sibling);
                    sibling = parent.left;
                }
                color(sibling, colorOf(parent));
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
        Queue<Node<K,V>> queue = new LinkedList<>();

        queue.offer(root);
        while (!queue.isEmpty()){
            Node<K, V> node = queue.poll();
            if (valEquals(value,node.value)){
                return true;
            }
            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return false;
    }
    private boolean valEquals(V v1,V v2){
        return v1 == null ? v2 == null:v1.equals(v2);
    }
    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) return;
        traversal(root,visitor);
    }
    private void traversal(Node<K,V> node,Visitor<K,V> visitor){
        if (node == null || visitor.stop) return;

        traversal(node.left,visitor);
        if (visitor.stop) return;
        visitor.visit(node.key, node.value);
        traversal(node.right,visitor);
    }
    /**
     * 下面是一些辅助方法
     */
    private Node<K,V> color(Node<K,V> node, boolean color){
        if (node == null) return null;
        node.color = color;
        return node;
    }
    // 将该节点染为红色
    private Node<K,V> red(Node<K,V> node){
        return color(node,RED);
    }
    // 将该节点染为黑色
    private Node<K,V> black(Node<K,V> node) {
        return color(node, BLACK);
    }
    // 返回该节点的颜色
    private boolean colorOf(Node<K,V> node) {
        return node == null ? BLACK : node.color;
    }
    // 该节点是否为黑色
    private boolean isBlack(Node<K,V> node) {
        return colorOf(node) == BLACK;
    }
    // 该节点是否为红色
    private boolean isRed(Node<K,V> node) {
        return colorOf(node) == RED;
    }
   
}
