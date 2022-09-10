package com.mengtu.letcode.list;

public class _2_twoNumAdd {
    public ListNode addTwoNumbers(ListNode l1,ListNode l2){
        ListNode pre = new ListNode(0);
        ListNode cur = pre;
        int carry = 0;
        while (l1 != null || l2 != null){
            int a = l1 != null ? l1.val:0;
            int b = l2 != null ? l2.val:0;
            int sum = a + b + carry;
            carry = sum/10;
            cur.next = new ListNode(sum % 10);
            cur = cur.next;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        if (carry > 0){
            cur.next = new ListNode(1);
        }
        return pre.next;
    }
    public ListNode addTwoNumbers2(ListNode l1,ListNode l2){
        ListNode pre = new ListNode(0);
        ListNode cur = pre;
        int carry = 0;
        while (l1 != null || l2 != null){
            int a = 0;
            int b = 0;
            if (l1 != null) {
                a = l1.val;
                l1 = l1.next;
            }

            if (l2 != null) {
                b = l2.val;
                l2 = l2.next;
            }
            int sum = a + b + carry;
            carry = sum/10;
            cur.next = new ListNode(sum % 10);
            cur = cur.next;


        }
        if (carry > 0){
            cur.next = new ListNode(1);
        }
        return pre.next;
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
