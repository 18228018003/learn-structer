package com.mengtu.structer.set;

import com.mengtu.structer.linear.List;
import com.mengtu.structer.linear.MyList;

public class ListSet<E> implements Set<E> {

    private List<E> list = new MyList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(E element) {
        return list.contains(element);
    }

    @Override
    public void add(E element) {
        if (list.contains(element)) return;
        list.add(element);
    }

    @Override
    public void remove(E element) {
        int index = list.indexOf(element);
        if (index != List.ELEMENT_NOT_FOUND){
            list.remove(index);
        }
    }

    @Override
    public void traversal(Visitor<E> visitor) {

    }
}
