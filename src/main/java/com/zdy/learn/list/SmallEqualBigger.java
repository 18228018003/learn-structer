package com.zdy.learn.list;

/**
 *  把一个链表按照大于等于小于某个数的区域划分
 * @author 周德永
 * @date 2021/10/27 23:55
 */
public class SmallEqualBigger {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
    }

    public static ListNode listPartition2(ListNode head,int pivot){
        ListNode sH = null; /*small head*/
        ListNode sT = null; /*small tail*/
        ListNode eH = null; /*equal head*/
        ListNode eT = null; /*equal tail*/
        ListNode bH = null; /*big head*/
        ListNode bT = null; /*big tail*/

        ListNode next = null; /*保存下一个节点*/
        while (head != null){
            next = head.next;
            head.next = null;
            if (head.val < pivot){
                if (sH == null){
                    sH = head;
                    sT = head;
                }else {
                    sT.next = head;
                    sT = head;
                }
            }else if (head.val == pivot){
                if (eH == null){
                    eH = head;
                    eT = head;
                }else {
                    eT.next = head;
                    eT = head;
                }
            }else {
                if (bH == null){
                    bH = head;
                    bT = head;
                }else {
                    bT.next = head;
                    bT = head;
                }
            }
            head = next;
        }
        /*samll and equal reconnect*/
        if (sT != null){
            sT.next = eH;
            eT = eT == null ? sT:eT;/*谁去连大于区域的头 谁就变eT*/
        }
        if (eT != null){
            eT.next = bH;
        }
        return sH != null ? sH:(eH != null ? eH:bH);
    }
}


