package com.zdy.learn.list;

/**
 *  打印两个升序链表的公共部分
 * @author 周德永
 * @date 2021/10/27 23:02
 */
public class PrintListCommon {
    public static void printTwoListCommon(ListNode node1,ListNode node2){
        while (node1 != null && node2 != null){
            if (node1.val < node2.val){
                node1 = node1.next;
            }else if (node1.val > node2.val){
                node2 = node2.next;
            }else {
                System.out.println(node1.val);
                node1 = node1.next;
                node2 = node2.next;
            }
        }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(3);
        head.next.next = new ListNode(6);
        head.next.next.next = new ListNode(8);
        head.next.next.next.next = new ListNode(9);

        ListNode node = new ListNode(2);
        node.next = new ListNode(5);
        node.next.next = new ListNode(8);
        node.next.next.next = new ListNode(9);
        node.next.next.next.next = new ListNode(23);

        printTwoListCommon(head,node);
    }
}
class ListNode {
     int val;
     ListNode next;
     ListNode() {}
     ListNode(int val) { this.val = val; }
}
