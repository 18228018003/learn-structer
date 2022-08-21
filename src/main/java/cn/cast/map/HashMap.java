package cn.cast.map;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

@SuppressWarnings("uncheck")
public class HashMap<K,V> implements Map<K,V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size;
    private Node<K,V>[] table;
    private static final int DEFAULT_CAPACITY = 1 << 4;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public HashMap(){
        table = new Node[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {
        if (size == 0) return;
        size = 0;
        Arrays.fill(table, null);
    }

    @Override
    public V put(K key, V value) {
        int index = index(key);
        Node<K,V> root = table[index];
        if (root == null){
            root = new Node<>(key,value,null);
            table[index] = root;
            size++;
            afterPut(root);
            return null;
        }
        //添加新的节点到红黑树上面去
        // 添加的不是第一个节点
        // 找到父节点
        Node<K,V> parent = root;
        Node<K,V> node = root;
        int cmp = 0;
        K k1 = key;
        int h1 = hash(k1);
        Node<K,V> result = null;
        boolean searched = false;
        do{
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2){
                cmp = 1;
            }else if (h1 < h2){
                cmp = -1;
            }else if (Objects.equals(k1,k2)){// 相等
                cmp = 0;
            }else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable<?>
                    && (cmp = ((Comparable<K>)k1).compareTo(k2)) != 0
            ){

            }else if (searched){//先扫描，然后再根据内存地址大小决定左右
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            }else { //searched == false;
                if (node.left != null && (result = node(node.left,k1)) != null
                        || node.right != null && (result = node(node.right,k1)) != null
                ){ //已经存在key
                    node = result;
                    cmp = 0;
                }else {
                    searched = true;
                    cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
                }
            }
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { // 相等
                V oldValue = node.value;
                node.key = key;
                node.value = value;
                node.hash = h1;
                return oldValue;
            }
        }while (node != null);
        // 看看插入到父节点的哪个位置
        Node<K,V> newNode = new Node<>(key,value,parent);
        if (cmp > 0){
            parent.right = newNode;
        }else {
            parent.left = newNode;
        }
        size++;
        afterPut(newNode);
        return null;
    }
    private void resize(){
        if (size / table.length <= DEFAULT_LOAD_FACTOR) return;
        Node<K,V>[] oldTable = table;
        table = new Node[oldTable.length << 1];
        Queue<Node<K,V>> queue = new LinkedList<>();
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] == null) continue;
            queue.offer(oldTable[i]);
            while (!queue.isEmpty()){
                Node<K, V> node = queue.poll();
                if (node.left != null){
                    queue.offer(node.left);
                }
                if (node.right != null){
                    queue.offer(node.right);
                }
                moveNode(node);
            }
        }
    }

    private void moveNode(Node<K,V> newNode) {
        // 重置
        newNode.parent = null;
        newNode.left = null;
        newNode.right = null;
        newNode.color = RED;

        int index = index(newNode);
        // 取出index位置的红黑树根节点
        Node<K, V> root = table[index];
        if (root == null) {
            root = newNode;
            table[index] = root;
            afterPut(root);
            return;
        }

        // 添加新的节点到红黑树上面
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        K k1 = newNode.key;
        int h1 = newNode.hash;
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (k1 != null && k2 != null
                    && k1 instanceof Comparable
                    && k1.getClass() == k2.getClass()
                    && (cmp = ((Comparable)k1).compareTo(k2)) != 0) {
            } else {
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            }

            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            }
        } while (node != null);

        // 看看插入到父节点的哪个位置
        newNode.parent = parent;
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }

        // 新添加节点之后的处理
        afterPut(newNode);
    }

    /**
     * 比较key
     * @param k1
     * @param k2
     * @return
     */
    private int compare(K k1, K k2, int h1, int h2) {
        //比较hash值
        int result = h1 - h2;
        if (result != 0) return result;

        //hash值相等
        if (Objects.equals(k1,k2)) return 0;

        //hash值相等 但是值不等 比较类型
        if (k1 != null && k2 != null){
            String k1Cls = k1.getClass().getName();
            String k2Cls = k2.getClass().getName();
            result = k1Cls.compareTo(k2Cls);
            if (result != 0) return result;
            //同一种类型
            if (k1 instanceof Comparable) return ((Comparable<K>)k1).compareTo(k2);
        }
        //同一种类型，hash值相等，但是不具备可比较性
        // k1不为null k2为null
        // k2不为null k1为null
        return System.identityHashCode(k1) - System.identityHashCode(k2);
    }


    /**
     * 根据Key 生成对应的索引(在桶组中的位置);
     * @param key
     * @return
     */
    private int index(K key){
        if (key == null) return 0;
        int hash = key.hashCode();
        hash = hash ^ (hash >>> 16);
        return hash & (table.length - 1);
    }
    private int index(Node<K, V> node) {
        return node.hash & (table.length - 1);
    }
    private int hash(K key){
        if (key == null) return 0;
        int hash = key.hashCode();
        return hash ^ (hash >>> 16);
    }
    @Override
    public V get(K key) {
        Node<K,V> node = node(key);
        return node(key) != null ? node.value : null;
    }

    private Node<K,V> node(K key) {
        int index = index(key);
        Node<K,V> node = table[index];
        return node == null ? null : node(node,key);
    }

    private Node<K,V> node(Node<K,V> node, K k1) {
        int h1 = hash(k1);
        Node<K,V> result = null;
        int cmp = 0;
        while (node != null){
            int h2 = node.hash;
            K k2 = node.key;
            //先比较hash值
            if (h1 > h2){
                node = node.right;
            }else if (h1 < h2){
                node = node.left;
            }else if (Objects.equals(k1,k2)) return node;
            else if (k1 != null && k2 != null
                    && k1.getClass()==k2.getClass()
                    && k1 instanceof Comparable<?>
                    && (cmp = ((Comparable)k1).compareTo(k2)) != 0
            ){
                node = cmp > 0 ? node.right : node.left;
                /*if (cmp > 0){
                    node = node.right;
                }else {
                    node = node.left;
                }*/
            }else if (node.right != null && (result = node(node.right,k1)) != null){//哈希值相等  不具备可比较性
                return result;
            }else {
                node = node.left;
            }
            /*else if (node.left != null && (result = node(node.left,k1)) != null){
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

    private V remove(Node<K,V> node) {

        if (node == null) return null;
        Node<K,V> oldNode = node;
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
        int index = index(node);
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
            afterRemove(replacement);
        }else if (node.parent == null){ // node是叶子节点并且是根节点
            table[index] = null;
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

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (size==0)return false;
        Queue<Node<K,V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            queue.offer(table[i]);
            while (!queue.isEmpty()){
                Node<K, V> node = queue.poll();
                if (Objects.equals(value,node.value)) return true;
                if (node.left != null){
                    queue.offer(node.left);
                }
                if (node.right != null){
                    queue.offer(node.right);
                }
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {

    }

    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return null;
        node.color = color;
        return node;
    }

    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    protected static class Node<K,V>{
        int hash;
        K key;
        V value;
        boolean color = RED;
        Node<K,V> left;
        Node<K,V> right;
        Node<K,V> parent;
        public Node(K key,V value,Node<K,V> parent){
            this.key = key;
            int hash = key == null ? 0 : key.hashCode();
            this.hash = hash ^ (hash >>> 16);
            this.value = value;
            this.parent = parent;
        }

        public boolean hasTwoChild(){
            return left != null && right != null;
        }
        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }

            if (isRightChild()) {
                return parent.left;
            }

            return null;
        }

        @Override
        public String toString() {
            return "Node_" + key + "_" + value;
        }
    }
    private void afterPut(Node<K,V> node) {
        Node<K,V> parent = node.parent;

        // 添加的是根节点 或者 上溢到达了根节点
        if (parent == null){
            black(node);
            return;
        }
        // 如果父节点是黑色，直接返回
        if (isBlack(parent)) return;
        Node<K,V> uncle = parent.sibling();// 叔父节点
        Node<K,V> grand = red(parent.parent);// 祖父节点
        if (isRed(uncle)){// 叔父节点是红色【B树节点上溢】
            black(parent);
            black(uncle);
            // 把祖父节点当做是新添加的节点
            afterPut(grand);
            return;
        }
        // 叔父节点不是红色
        if (parent.isLeftChild()){ //L
            if (node.isLeftChild()){ //LL
                black(parent);
            }else {//LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        }else {
            if (node.isLeftChild()) { // RL
                black(node);
                rotateRight(parent);
            } else { // RR
                black(parent);
            }
            rotateLeft(grand);
        }
    }
    private void rotateLeft(Node<K, V> grand){
        Node<K,V> parent = grand.right;
        Node<K,V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    private void rotateRight(Node<K, V> grand){
        Node<K,V> parent = grand.left;
        Node<K,V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
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
    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
        parent.parent = grand.parent;
        if (grand.isLeftChild()){
            grand.parent.left = parent;
        }else if (grand.isRightChild()){
            grand.parent.right = parent;
        }else {// grand是root节点

            table[index(grand)] = parent;
        }
        if (child != null){
            child.parent = grand;
        }
        grand.parent = parent;
    }



    private void KeyNotNullCheck(K key) {
        if (key == null){
            throw new RuntimeException("key must not be null!!!");
        }
    }

}