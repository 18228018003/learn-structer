package cn.cast.leetcode.string;

/**
 * description
 *
 * @author 周德永
 * @date 2022/1/25 16:27
 */
public class 剑指Offer06从尾到头打印链表 {
    public static void main(String[] args) {

    }
    public int[] reversePrint(ListNode head) {

        ListNode cur = head;
        int count = 0;
        while (cur != null)
        {
            count++;
            cur = cur.next;
        }
        int[] res = new int[count];
        cur = head;
        while (cur!=null && count>=0)
        {
            res[--count] = cur.val;
            cur = cur.next;
        }
        return res;
    }
     static class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
    }
}
