package com.mengtu.letcode.stack;

public class MinStack {
//    Stack<Integer> stack;
//    Stack<Integer> minStack;
//
//    public MinStack() {
//        stack = new Stack<>();
//        minStack = new Stack<>();
//    }
//
//    public void push(int val) {
//        stack.push(val);
//        if (minStack.isEmpty()){
//            minStack.push(val);
//        }else {
//            minStack.push(Math.min(minStack.peek(),val));
//        }
//    }
//
//    public void pop() {
//        stack.pop();
//        minStack.pop();
//    }
//
//    public int top() {
//        return stack.peek();
//    }
//
//    public int getMin() {
//        return minStack.peek();
//    }
    Node node;
    public MinStack(){
        node = new Node(-1,Integer.MAX_VALUE,null);
    }
    public void push(int val) {
        node = new Node(val,Math.min(val, node.min),node);
    }

    public void pop() {
        node = node.next;
    }

    public int top() {
        return node.val;
    }

    public int getMin() {
        return node.min;
    }
    private static class Node{
        public int val;
        public int min;
        public Node next;
        public Node(int val,int min,Node next){
            this.min = min;
            this.next = next;
            this.val = val;
        }
    }
}
