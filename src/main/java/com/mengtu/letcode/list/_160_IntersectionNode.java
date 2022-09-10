package com.mengtu.letcode.list;

//相交链表
public class _160_IntersectionNode {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        ListNode curA = headA,curB = headB;
        while (curA != curB){
//            curA = (curA.next == null) ? headA : curA.next;
//            curB = (curB.next == null) ? headB : curB.next;
            curA = (curA == null) ? headA : curA.next;
            curB = (curB == null) ? headB : curB.next;
        }
        return curA;
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
