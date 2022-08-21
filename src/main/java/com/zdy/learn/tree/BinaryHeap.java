package com.zdy.learn.tree;

import java.util.Comparator;

/**
 * description
 *
 * @author 周德永
 * @date 2022/3/13 23:13
 */
public class BinaryHeap<E> extends AbstractHeap<E> {
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap(E[] elements, Comparator<E> comparator){
        super(comparator);
        if (elements == null || elements.length == 0){
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        }else {
            size = elements.length;
            int capacity = Math.max(elements.length,DEFAULT_CAPACITY);
            this.elements = (E[]) new Object[capacity];
            for (int i = 0; i < elements.length; i++) {
                this.elements[i] = elements[i];
            }
            heapify();
        }
    }

    public BinaryHeap(E[] elements){
        this(elements,null);
    }
    public BinaryHeap(Comparator<E> comparator){
        this(null,comparator);
    }
    public BinaryHeap(){
        this(null,null);
    }

    @Override
    public int size() {
        return 0;
    }

    /**
     * 有两种堆化的方式，
     * 1、一种自上而下的上滤
     * 2、一种是自下而上的下滤
     */
    private void heapify() {
        //叶子节点不需要下滤 非叶子节点的数量 = n/2
        for (int i = (size >> 1) -1; i >= 0 ; i--) {
            siftDown(i);
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        ensureCapacity(size + 1);
        elements[size++] = element;
        siftUp(size - 1);
    }

    //上滤
    private void siftUp(int index) {
       /* //新添加的节点
        E e = elements[index];
        while (index > 0){
            int pindex = (index-1) >> 1;
            E p = elements[pindex];
            if (compare(e,p) <= 0) return;
            //交换 可优化
            E tmp = elements[pindex];
            elements[pindex] = elements[index];
            elements[index] = tmp;
            //重新赋值
            index = pindex;
        }*/
        E e = elements[index];
        while (index > 0){
            int pindex = (index-1) >> 1;
            E p = elements[pindex];
            if (compare(e,p) < 0) break;
            //将父元素存储在index位置
            elements[index] = p;
            index = pindex;
        }
        elements[index] = e;
    }

    private void ensureCapacity(int capacity) {
        int oldCap = elements.length;
        if (oldCap >= capacity) return;
        int newCapacity = oldCap + (oldCap >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        if (size >= 0) System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

    private void elementNotNullCheck(E element) {
        if (element == null){
            throw new IllegalArgumentException("element can not be null!");
        }
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }

    private void emptyCheck() {
        if (size == 0){
            throw new RuntimeException("错误");
        }
    }

    @Override
    public E remove() {
        emptyCheck();
        E root = elements[0];
        int lastIndex = --size;
        elements[0] = elements[lastIndex];
        elements[lastIndex] = null;
        siftDown(0);
        return root;
    }

    @Override
    public E replace(E element) {
        elementNotNullCheck(element);
        E root = null;
        if (size == 0){
            elements[0] = element;
            size++;
        }else {
            root = elements[0];
            elements[0] = element;
            siftDown(0);
        }
        return root;
    }

    private void siftDown(int index) {
        E element = elements[index];
        int  half = size >> 1;
        //第一个叶子节点的索引==非叶子节点的数量;
        while (index < half) { //必须保证index位置是叶子节点
//       index的节点有两种情况 1.只有左子节点 2.同时有左右子节点
            int childIndex = (index << 1)+1;
            int rightIndex = childIndex+1;
            E child = elements[childIndex];
            //选出左右子节点中最大的节点
            if (rightIndex < size && compare(elements[rightIndex],child) > 0){
                child = elements[childIndex = rightIndex];
            }
            if (compare(element,child) >= 0) break;
            //将子节点存放在Index位置
            elements[index] = child;
            //重新设置index
            index = childIndex;
        }
        elements[index] = element;
    }
}
