package com.mengtu.structer.heap;

import java.util.Comparator;

public class PriorityQueue<E> {
    private BinaryHeap<E> binaryHeap;
    public PriorityQueue(Comparator<E> comparator){
        binaryHeap = new BinaryHeap<>(comparator);
    }
    public PriorityQueue(){
        this(null);
    }

    public int size(){
        return binaryHeap.size();
    }
    public boolean isEmpty(){
        return binaryHeap.isEmpty();
    }
    public void clear(){
        binaryHeap.clear();
    }
    public void enQueue(E element){
        binaryHeap.add(element);
    }

    public E deQueue(){
        return binaryHeap.remove();
    }

    public E front(){
        return binaryHeap.get();
    }

}
