package cn.cast.leetcode.string;

import java.util.Stack;

/**
 * description
 *
 * @author 周德永
 * @date 2022/1/25 16:56
 */
public class 剑指Offer09用两个栈实现队列 {
    public static void main(String[] args) {

    }
    Stack<Integer> A, B;
    public 剑指Offer09用两个栈实现队列() {
        A = new Stack<>();
        B = new Stack<>();
    }
    public void appendTail(int value) {
        A.push(value);
    }
    public int deleteHead() {
        if(!B.isEmpty()) return B.pop();
        if(A.isEmpty()) return -1;
        while(!A.isEmpty())
            B.push(A.pop());
        return B.pop();
    }

}
