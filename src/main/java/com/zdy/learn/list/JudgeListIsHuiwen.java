package com.zdy.learn.list;

import java.util.Stack;

/**
 *  判断一个链表是不是回文链表
 * @author 周德永
 * @date 2021/10/27 23:22
 */
public class JudgeListIsHuiwen {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
    }

    public static boolean isHuiwen(ListNode node){
        Stack<ListNode> nodes = new Stack<>();
        ListNode cur = node;
        while (cur != null){
            nodes.add(node);
            cur = cur.next;
        }
        while (node != null){
            ListNode pop = nodes.pop();
            if (pop.val != node.val) return false;
            node = node.next;
        }
        return true;
    }
    public static boolean isHuiwen2(ListNode node){
        ListNode fast = node.next.next;
        ListNode slow = node.next;
        while (fast != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        ListNode prev = null;
        ListNode cur = slow;
        ListNode next = null;
        while (cur != null){
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        while (prev!=null){
            prev = prev.next;
            slow = slow.next;
            if (prev.val != slow.val){
                return false;
            }
        }

        return true;
    }
}
