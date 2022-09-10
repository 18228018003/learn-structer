package com.mengtu.letcode.list;

public class _86_Partition {
    public ListNode partition(ListNode head, int x){
        if (head == null) return null;
        ListNode leftHead = new ListNode(0);
        ListNode leftTail = leftHead;

        ListNode rightHead = new ListNode(0);
        ListNode rightTail = rightHead;

        while (head != null){
            if (head.val < x){
                leftTail.next = head;
                leftTail = head;
            }else {
                rightTail.next = head;
                rightTail = head;
            }
            head = head.next;
        }
        //原链表倒数第N个节点的值是 >= x的 A后面的所有节点的值都是小于x
        rightTail.next = null;
        leftTail.next = rightHead.next;
        return leftHead.next;
    }
    public static class ListNode{
        public ListNode(int val){
            this.val = val;
        }
        int val;
        ListNode head = null;
        ListNode next = null;
    }
}
