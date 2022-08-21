package com.mengtu.structer.map;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

@SuppressWarnings({"unchecked"})
public class HashMap<K,V> implements Map<K,V>{
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private int size;
    private Node<K,V>[] table;
    private static final int DEFAULT_CAPACITY = 1 << 4;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public HashMap(){
        table = new Node[DEFAULT_CAPACITY];
    }
    private void resize(){
        if ((float)size / table.length > DEFAULT_LOAD_FACTOR) return;
        Node<K, V>[] oldTable = table;
        table = new Node[table.length << 1];
        /*size = 0;
        Queue<Node<K,V>> queue = new LinkedList<>();
        for (Node<K, V> kvNode : oldTable) {
            if (kvNode == null) continue;
            queue.offer(kvNode);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                put(node.key, node.value);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }*/
        Queue<Node<K,V>> queue = new LinkedList<>();
        for (Node<K, V> kvNode : oldTable) {
            if (kvNode == null) continue;
            queue.offer(kvNode);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                moveNode(node);
            }
        }
    }

    private void moveNode(Node<K, V> newNode) {
        //重置
        newNode.parent = null;
        newNode.left = null;
        newNode.right = null;
        newNode.color = RED;
        int index = index(newNode);
        //取出index位置的红黑树根节点
        Node<K, V> root = table[index];
        if (root == null){
            root = newNode;
            table[index] = root;
            fixAfterPut(root);
            return;
        }
        //添加新的节点到红黑树上
        // 添加的不是第一个节点, 找到父节点
        Node<K,V> node = root;
        Node<K,V> parent = root;
        int cmp = 0;
        K k1 = newNode.key;
        int h1 = newNode.hash;
        boolean search = false;
        do {
            parent = node;// 父节点
            // 比较【传入节点的元素值】与【父节点的元素值】
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2){
                cmp = 1;
            }else if (h1 < h2){
                cmp = -1;
            }else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (cmp = ((Comparable<K>) k1).compareTo(k2)) != 0){
            }else {
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            }

            if (cmp > 0){
                node = node.right;
            }else if (cmp < 0){
                node = node.left;
            }
        }while (node != null);
        newNode.parent = parent;
        fixAfterPut(newNode);
    }
    protected Node<K,V> createNode(K key,V value,Node<K,V> parent){
        return new Node<>(key,value,parent);
    }
    protected static class Node<K,V>{
        boolean color = RED;
        int hash;
        K key;
        V value;
        Node<K,V> left;
        Node<K,V> right;
        Node<K,V> parent;
        public Node(K key,V value, Node<K,V> parent){
            this.key = key;
            int hash = key == null ? 0 : key.hashCode();
            this.hash = hash ^ (hash >>> 16);
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
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public V put(K key, V value) {
        int index = index(key);
        //取出index位置的红黑树根节点
        Node<K, V> root = table[index];
        if (root == null){
            root = createNode(key,value,null);
            table[index] = root;
            size++;
            return null;
        }
        //添加新的节点到红黑树上
        // 添加的不是第一个节点, 找到父节点
        Node<K,V> node = root;
        Node<K,V> parent = root;
        Node<K,V> result = null;
        int cmp = 0;
        int h1 = key == null ? 0 : key.hashCode();
        boolean search = false;
        do {
            parent = node;// 父节点
            // 比较【传入节点的元素值】与【父节点的元素值】
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2){
                cmp = 1;
            }else if (h1 < h2){
                cmp = -1;
            }else if (Objects.equals(key,k2)){
                cmp = 0;
            }else if (key != null && k2 != null
                    && key.getClass() == k2.getClass()
                    && key instanceof Comparable
                    && (cmp = ((Comparable<K>) key).compareTo(k2)) != 0){

            }else if (search){
                cmp = System.identityHashCode(key) - System.identityHashCode(k2);
            }else {
                if (node.left != null && (result = node(node.left,key)) != null
                        || (node.right != null &&  (result = node(node.right,key)) != null)){
                    node = result;
                    cmp = 0;
                }else { //不存在这个可以
                    search = true;
                    cmp = System.identityHashCode(key) - System.identityHashCode(k2);
                }
            }

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
        }while (node != null);
        Node<K,V> newNode = createNode(key,value,parent);
        if (cmp > 0){
            parent.right = newNode;
        }else {
            parent.left = newNode;
        }
        fixAfterPut(newNode);
        size++;
        return null;
    }

    /**
     * 节点元素比较
     * @param k1 k1
     * @param k2 k2
     * @param h1 h1
     * @param h2 h2
     * @return 1 0 -1
     */
    private int compare(K k1,K k2,int h1,int h2){
        //比较hash值
        int result = h1 - h2;
        if (result != 0) return result;
        //比较equals
        if (Objects.equals(k1,k2)) return 0;
        //hash值相等 但是不等
        //比较类名
        if (k1 != null && k2 != null){
            String k1Cls = k1.getClass().getName();
            String k2Cls = k2.getClass().getName();
            result = k1Cls.compareTo(k2Cls);
            if (result != 0) return result;
            //同一种类型 并且具备可比较性
            if (k1 instanceof Comparable){
                return ((Comparable<K>) k1).compareTo(k2);
            }
        }
        //同一类型 但是不具备可比较性
        //k1 不为空 k2 为空
        //k1 为null k2 不为空

        return System.identityHashCode(k1) - System.identityHashCode(k2);
    }
    private void fixAfterPut(Node<K,V> node){
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
            fixAfterPut(grand);
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
            table[index(grand)] = parent;
        }
        /*child节点的父节点*/
        if (child != null){
            child.parent = grand;
        }
        /*grand节点的父节点*/
        grand.parent = parent;

    }
    /**
     *  根据key生成对应索引(在数组桶中的位置)
     * @param key key
     * @return 索引
     */
    private int index(K key){
        if (key == null) return 0;
        int hash = key.hashCode();

        return  (hash ^ (hash >>> 16)) & (table.length - 1);
    }

    private int index(Node<K,V> node){
        return  node.hash & (table.length - 1);
    }
    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node != null ? node.value : null;
    }
    private Node<K,V> node(K key){
        int index = index(key);
        Node<K, V> node = table[index];
        int cmp;
        int h1 = key == null ? 0 : key.hashCode();
        while (node != null){
            cmp = compare(key,node.key,h1,node.hash);
            if (cmp == 0) return node;
            if (cmp > 0){
                node = node.right;
            }else {
                node = node.left;
            }
        }
        return null;
    }
    private Node<K,V> node1(K key){
        int index = index(key);
        Node<K, V> root = table[index];
        return root==null ? null : node(root,key);
    }
    private Node<K,V> node(Node<K,V> node,K key){
        int cmp;
        int h1 = key == null ? 0 : key.hashCode();
        Node<K,V> result = null;
        while (node != null){
            K k2 = node.key;
            int h2 = node.hash;
            //先比较hash值
            if (h1 > h2){
                node = node.right;
            }else if (h1 < h2){
                node = node.left;
            }else if (Objects.equals(key,k2)){
                return node;
            } else if (key != null && k2 != null
                    && key.getClass() == k2.getClass()
                    && key instanceof Comparable){
                    cmp = ((Comparable<K>) key).compareTo(k2);
                if (cmp > 0){
                    node = node.right;
                }else if (cmp < 0){
                    node = node.left;
                }else {
                    return node;
                }
            }else if (node.right != null && (result = node(node.right,key)) != null){ //哈希值相等，不具备可比较性,也不equals
                return result;
            }else {
                node = node.left;
            }
            /*else if (node.left != null && (result = node(node.left,key)) != null){
                return result;
            }else {
                return null;
            }*/
        }
        return null;
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
    protected V remove(Node<K,V> node){
        if (node == null) return null;
        Node<K,V> oldNode = node;
        Node<K,V> tempNode = node;
        int index = index(node);
        size--;

        if (node.hasTwoChild()){ //度为2的节点
            // 找到后继节点
            Node<K,V> s = successor(node);
            node.key = s.key;
            node.value = s.value;
            node.hash = s.hash;
            node = s;
        }
        // 删除node节点（node的度必然是1或者0）
        Node<K,V> replacement = node.left != null ? node.left : node.right;
        if (replacement != null){
            // 核心：用子节点替代原节点的位置
            // 更改parent
            replacement.parent = node.parent;
            if (node.parent == null){ // node是度为1的节点并且是根节点
                table[index] = replacement;
            }else if (node.parent.left == node){
                node.parent.left = replacement;
            }else {
                node.parent.right = replacement;
            }
            // 删除节点之后的处理，BST中无需处理，为 AVL树 和 B树提供可覆盖的方法
            fixAfterRemove(replacement);
        }else if (node.parent == null){ // node是叶子节点并且是根节点
            table[index] = null;
            // 删除节点之后的处理，BST中无需处理，为 AVL树 和 B树提供可覆盖的方法
            fixAfterRemove(node);
        }else {
            if (node.parent.left == node){
                node.parent.left = null;
            }else {
                node.parent.right = null;
            }
            // 删除节点之后的处理，BST中无需处理，为 AVL树 和 B树提供可覆盖的方法
            fixAfterRemove(node);
        }
        afterRemove(tempNode,node);
        return oldNode.value;
    }
    protected void afterRemove(Node<K,V> willNode,Node<K,V> removeNode){

    }
    private void fixAfterRemove(Node<K,V> node) {
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
                    fixAfterRemove(parent);
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
                    fixAfterRemove(parent);
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
    public void clear() {
        if (size == 0) return;
        size = 0;
        for (int i = 0; i < table.length; i++){
            table[i] = null;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (size == 0) return false;
        Queue<Node<K,V>> queue = new LinkedList<>();
        for (Node<K, V> kvNode : table) {
            if (kvNode == null) continue;
            queue.offer(kvNode);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (Objects.equals(node.value, value)) return true;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (size == 0 || visitor == null) return;
        Queue<Node<K,V>> queue = new LinkedList<>();
        for (Node<K, V> kvNode : table) {
            if (kvNode == null) continue;
            queue.offer(kvNode);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (visitor.visit(kvNode.key, kvNode.value)) return;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
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
