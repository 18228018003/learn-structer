package com.mengtu.structer.linear;

public class MyList<E> extends AbstractList<E> {
    private int size;
    private Node<E> firstNode;

    private static class Node<E>{
        E element;
        Node<E> next;
        public Node(E element,Node<E> next){
            this.element = element;
            this.next = next;
        }
    }

    public void clear(){
        size = 0;
        firstNode = null;
    }
    private Node<E> node(int index){
        Node<E> node = firstNode;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }
    @Override
    public boolean contains(E element) {
        return false;
    }

    @Override
    public E get(int index) {
        return node(index).element;
    }

    @Override
    public E set(int index, E element) {
        Node<E> node = node(index);
        E old = node.element;
        node.element = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        if (index == 0){
           firstNode = new Node<>(element, firstNode);
        }else {
            Node<E> prev = node(index - 1);
            prev.next = new Node<>(element, prev.next);
        }
        size++;
    }
    public void reverse(){
        Node<E> prev = null;
        Node<E> cur = firstNode;
        Node<E> next = firstNode.next;
        while (next != null){
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
    }
    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(E element) {
        Node<E> node = firstNode;
        if (element == null){
            for (int i = 0; i < size; i++) {
                if (node.element == null) return i;
            }
        }else {
            for (int i = 0; i < size; i++){
                if (element.equals(node.element)) return i;
                node = node.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }
}
