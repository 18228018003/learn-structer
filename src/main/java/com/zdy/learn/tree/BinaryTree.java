package com.zdy.learn.tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉树
 *
 * @author 周德永
 * @date 2021/10/28 0:11
 */
public class BinaryTree {
    public static class Node {
        private int val;
        private Node left;
        private Node right;

        public Node(int val) {
            this.val = val;
        }
    }

    /*先序遍历*/
    public static void preOrderRecur(Node head) {
        System.out.println(head.val);
        if (head.left != null) {
            preOrderRecur(head.left);
        }
        if (head.right != null) {
            preOrderRecur(head.right);
        }
    }

    /*先序遍历非递归版*/
    public static void preOrderUnRecur(Node head) {
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            stack.push(head);
            while (!stack.isEmpty()) {
                head = stack.pop();
                System.out.print(head.val + " ");
                if (head.right != null) {
                    stack.add(head.right);
                }
                if (head.left != null) {
                    stack.add(head.left);
                }
            }
        }
        System.out.println();
    }

    /*后序遍历*/
    public static void posOrderUnRecur(Node head) {
        System.out.println("pos-order");
        if (head != null) {
            Stack<Node> s1 = new Stack<>();
            Stack<Node> s2 = new Stack<>();
            s1.add(head);
            while (!s1.isEmpty()) {
                head = s1.pop();
                s2.push(head);
                if (head.left != null) {
                    s1.push(head.left);
                }
                if (head.right != null) {
                    s1.push(head.right);
                }
            }
            while (!s2.isEmpty()) {
                System.out.print(s2.pop().val + " ");
            }
        }
    }


    /*中序遍历*/
    public void inOrderUnRecur(Node node)
    {
        if (node != null){
            Stack<Node> stack = new Stack<>();
            while (!stack.isEmpty() || node != null){
                if (node != null){
                    stack.push(node);
                    node = node.left;
                }else {
                    node = stack.pop();
                    System.out.print(node.val+" ");
                    node = node.right;
                }
            }
        }
    }
    public void test(Node node)
    {
        if (node != null){
            Stack<Node> stack = new Stack<>();
            while (!stack.isEmpty() || node != null){
                if (node != null){
                    stack.push(node);
                    node = node.left;
                }else {
                    node = stack.pop();
                    System.out.println(node.val);
                    node = node.right;
                }
            }
        }
    }    /*树的宽度优先遍历*/
    public void w(Node node) {
        if (node == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()){
            node = queue.poll();
            System.out.println(node);
            if (node.left != null)
            {
                queue.add(node.left);
            }
            if (node.right != null){
                queue.add(node.right);
            }
        }
    }
    /**
     * 求一棵树的最大宽度
     */
    public int getMaxWeight(Node node)
    {
        if (node == null){
            return 0;
        }
        HashMap<Node,Integer> levelMap = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        levelMap.put(node,1);
        int curLevel = 1;
        int curLevelNodes = 0;
        int max = -1;
        while (!queue.isEmpty()){
            Node cur = queue.poll();
            Integer curNodeLevel = levelMap.get(cur);
            if (curNodeLevel == curLevel){
                curLevelNodes++;
            }else {
                max = Math.max(max,curLevelNodes);
                curLevel++;
                curLevelNodes = 1;
            }
            if (cur.left != null){
                levelMap.put(cur.left,curNodeLevel+1);
                queue.add(cur.left);
            }
            if (cur.right != null){
                levelMap.put(cur.right,curNodeLevel+1);
                queue.add(cur.left);
            }
        }
        return max;
    }
    public Integer preValue = Integer.MIN_VALUE;
    public boolean checkBst(Node node){
        if (node == null){
            return true;
        }
        boolean bst = checkBst(node.left);
        if (!bst){
            return false;
        }
        if (node.val <= preValue){
            return false;
        }else {
            preValue = node.val;
        }

        return checkBst(node.right);

    }

    /**
     * 判断一棵树是否是搜索二叉树
     */
    public boolean isBST(Node node){
        if (node!=null){
            int minValue = Integer.MIN_VALUE;
            Stack<Node> stack = new Stack<>();
            while (!stack.isEmpty() || node != null){
                if (node != null){
                    stack.push(node);
                    node = node.left;
                }else {
                    node = stack.pop();
                    if (node.val <= minValue){
                        return false;
                    }else {
                        minValue = node.val;
                    }
                }
            }
        }
        return true;
    }

    /*判断一棵树是否是完全二叉树*/
    public boolean isCBT(Node node)
    {
        if (node == null){
            return true;
        }

        /*是否遇到了左右子节点不全的节点*/
        boolean leaf = false;
        Queue<Node> queue = new LinkedList<>();
        Node left;
        Node right;
        queue.add(node);

        while (!queue.isEmpty()){
            node = queue.poll();
            left = node.left;
            right = node.right;
            if (
                    (leaf && (left != null || right != null))
                    ||
                    (left == null && right != null)
            ){
                return false;
            }
            if (left != null){
                queue.add(left);
            }
            if (right != null){
                queue.add(right);
            }
            if (left == null || right == null){
                leaf = true;
            }
        }
        return true;
    }

    public static class ReturnType{
        public boolean isBalanced;
        public int height;

        public ReturnType(boolean isBalanced,int height){
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }

    public static ReturnType process(Node x){
        if (x == null){
            return new ReturnType(true,0);
        }
        ReturnType leftData = process(x.left);
        ReturnType rightData = process(x.right);
        int height = Math.max(leftData.height,rightData.height)+1;
        boolean isBalanced = leftData.isBalanced && rightData.isBalanced
                && Math.abs(leftData.height-rightData.height) <= 1;
        return new ReturnType(isBalanced,height);
    }
}
