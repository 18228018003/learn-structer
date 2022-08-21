package cn.cast.list.stack;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/20 16:35
 */
public class Stack<E> {
    private List<E> list = new ArrayList<>();

    public void clear() {
        list.clear();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void push(E element) {
        list.add(element);
    }


    public E pop() {
        return list.remove(list.size() - 1);
    }


    public E top() {
        return list.get(list.size() - 1);
    }
}
