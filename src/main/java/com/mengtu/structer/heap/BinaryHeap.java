package com.mengtu.structer.heap;

import java.util.Comparator;

public class BinaryHeap<E> extends AbstractHeap<E>{

    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap(E[] elements,Comparator<E> comparator){
        super(comparator);
        if (elements == null || elements.length == 0){
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        }else {
            int length = elements.length;
            int capacity = Math.max(length,DEFAULT_CAPACITY);
            size = length;
            this.elements = (E[]) new Object[capacity];;
            for (int i = 0; i < elements.length; i++) {
                this.elements[i] = elements[i];
            }
            heapify();
        }

    }

    private void heapify() {
        //自上而下的上滤
//        for (int i = 1; i < size; i++) {
//            siftUp(i);
//        }        
        //自下而上的下滤
        for (int i = (size >> 1) - 1; i >= 0 ; i--) {
            siftDown(i);
        }
    }

    public BinaryHeap(E[] elements){
        this(elements,null);
    }
    public BinaryHeap(Comparator<E> c){
        this(null,c);
    }
    public BinaryHeap(){
        this(null,null);
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
        ensureCapacity(size+1);
        elements[size++] = element;
        siftUp(size-1);
    }

    private void siftUp(int index){
//        E element = elements[index];
//        while (index > 0){
//            int pIndex = (index >> 1) - 1;
//            E parent = elements[pIndex];
//            if (compare(element,parent) <= 0) return;
//            //交换
//            E temp = elements[index];
//            elements[index] = elements[pIndex];
//            elements[pIndex] = temp;
//            index = pIndex;
//        }
        E e = elements[index];
        while (index > 0){
            int pIndex = (index - 1) >> 1;
            E p = elements[pIndex];
            if (compare(e,p) <= 0) break;

            //将父元素存储在index位置
            elements[index] = p;
            //重新赋值
            index = pIndex;
        }
        elements[index] = e;

    }
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;

        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        if (size >= 0) System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }
    private void emptyCheck(){
        if (size == 0){
            throw new IndexOutOfBoundsException("heap is empty");
        }
    }
    private void elementNotNullCheck(E element){
        if (element == null){
            throw new IllegalArgumentException("element must not be null");
        }
    }
    @Override
    public E remove() {
        emptyCheck();
        int lastIndex = --size;
        E root = elements[0];
        elements[0] = elements[lastIndex];
        elements[lastIndex] = null;
        siftDown(0);
        return root;
    }

    /**
     * index位置的元素下滤
     * @param index
     */
    private void siftDown(int index) {
        //第一个叶子节点的索引 等于非叶子节点的数量
        E e = elements[index];
        int half = size >> 1;
        while (index < half) { //必须保证index位置是非叶子节点
//            index的节点有两种情况
            //1. 只有左子节点
            //2. 同时有左右子节点
            //默认为左子节点
            int childIndex = (index << 1) + 1;
            E child = elements[childIndex];
            //右子节点
            int rightIndex = childIndex + 1;
            //选出左右子节点中最大的
            if (rightIndex < size && compare(elements[rightIndex],child) > 0){
                child = elements[childIndex = rightIndex];
            }

            if (compare(child,e) <= 0) break;

            //将子节点存放在index位置
            elements[index] = child;
            index = childIndex;
        }
        elements[index] = e;

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
}
