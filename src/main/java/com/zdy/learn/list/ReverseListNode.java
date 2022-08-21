package com.zdy.learn.list;

/**
 *  反转单链表
 * @author 周德永
 * @date 2021/10/27 22:30
 */
public class ReverseListNode {
    public static class Node{
        private Node next;
        private String name;
    }

    private Node reverseNode(Node head){

        Node prev = null;
        Node cur = head;
        while (cur != null){
            Node tempNext = cur.next; //下一个节点
            cur.next = prev;    /*当前节点指向前一个节点*/
            prev = cur;         /*前一个节点往后移*/
            cur = tempNext;     /*当前节点往后移*/
        }

        return null;
    }
}
