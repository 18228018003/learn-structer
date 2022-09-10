package com.mengtu.letcode.list;

//回文链表
public class _234_Palindrome {
    public boolean isPalindrome(ListNode head) {
        if (head==null || head.next == null) return true;
        if (head.next.next == null) return head.val == head.next.val;
        ListNode mid = middleNode(head);
        ListNode rightHead = reverseList(mid.next);
        ListNode leftHead = head;
        while (rightHead != null){
            if(leftHead.val != rightHead.val) return false;
            rightHead = rightHead.next;
            leftHead = leftHead.next;
        }
        return true;
    }

    private ListNode reverseList(ListNode node) {
        ListNode pre = null;
        ListNode cur = node;
        ListNode next = null;
        while (cur != null){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    private ListNode middleNode(ListNode head) {
        ListNode fast = head.next;
        ListNode slow = head;
        while ( fast!=null && fast.next != null ){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

}
