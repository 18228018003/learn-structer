package com.mengtu.structer.linear;

public class MyStack<E>{

    private MyArray<E> list = new MyArray<>();

    public void push(E element){
        list.add(element);
    }
    E pop(){
        return list.remove(list.size()-1);
    }
    E top(){
        return list.get(list.size() - 1);
    }
}
