package com.mengtu.structer.linear;

public abstract class AbstractList<E> implements List<E>{
    protected int size;

    @Override
    public void clear() {

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
    public void add(E element) {
        add(size,element);
    }
}
