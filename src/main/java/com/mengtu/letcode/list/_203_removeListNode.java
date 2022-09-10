package com.mengtu.letcode.list;

public class _203_removeListNode {
    public ListNode removeListNode(ListNode head,int val){
        if (head == null) return null;
        ListNode newHead = new ListNode(0); //新链表的头结点
        ListNode newTail = newHead; //新链表的尾结点
//
//        while (head != null){
//            if (head.val != val){
//                if (newTail == null){
//                    newTail = head;
//                    newHead = head;
//                }else {
//                    newTail.next = head;
//                    newTail = head;
//                }
//            }
//            head = head.next;
//        }
        while (head != null){
            if (head.val != val){
                newTail.next = head;
                newTail = head;
            }
            head = head.next;
        }
        newTail.next = null;
        return newHead.next;
    }
}
