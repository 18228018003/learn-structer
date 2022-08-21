package com.zdy.learn.list;

/**
 *  反转双向链表
 * @author 周德永
 * @date 2021/10/27 22:49
 */
public class ReverseListDouble {
    public static class Node<T>{
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value,Node<T> prev,Node<T> next){
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

    }

    public Node<Integer>  reverse(Node<Integer> head){
        Node<Integer> prev = null;
        Node<Integer> next = null;

        while (head != null){
            next = head.next;
            head.next = prev;
            head.prev = next;
            prev = head;
            head = next;
        }
        return prev;
    }

}

