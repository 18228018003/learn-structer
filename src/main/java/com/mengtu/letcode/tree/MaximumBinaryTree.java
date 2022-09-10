package com.mengtu.letcode.tree;


import org.junit.Test;

import java.util.Arrays;
import java.util.Stack;

public class MaximumBinaryTree {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return findRoot(nums,0,nums.length);
    }
    private TreeNode findRoot(int[] nums,int left,int right){
        if (left == right) return null;
        int maxIdx = left;
        for (int i = left + 1; i < right; i++) {
            if (nums[i] > nums[maxIdx]) maxIdx = i;
        }
        TreeNode root = new TreeNode(nums[maxIdx]);
        root.left = findRoot(nums,left,maxIdx);
        root.left =findRoot(nums,maxIdx + 1,right);
        return root;
    }
    public TreeNode constructMinimumBinaryTree(int[] nums) {
        return findMinRoot(nums,0,nums.length);
    }

    private TreeNode findMinRoot(int[] nums, int l, int r) {
        if (l == r) return null;
        int minIndex = l;
        for (int i = l + 1; i < r; i++) {
            if (nums[i] < nums[minIndex]) minIndex = i;
        }
        TreeNode root = new TreeNode(nums[minIndex]);
        root.left = findMinRoot(nums,l,minIndex);
        root.right = findMinRoot(nums,minIndex+1,r);
        return root;
    }
    //返回一个数组 数组中存放每个节点父节点的索引（如果没有父节点，存-1）
    public int[] parentIndexes(int[] nums){
        int[] lis = new int[nums.length];
        int[] ris = new int[nums.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < nums.length; i++) {
            ris[i] = -1;
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]){
                ris[stack.pop()] = i;
            }
            lis[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        int[] pis = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (lis[i] == -1 && ris[i] == -1){
                pis[i] = -1;
                continue;
            }
            if (lis[i] == -1){
                pis[i] = ris[i];
            }else if (ris[i] == -1){
                pis[i] = lis[i];
            }else if (nums[ris[i]] < nums[lis[i]]){
                pis[i] = ris[i];
            }else {
                pis[i] = lis[i];
            }

        }
        return pis;
    }
    @Test
    public void testM(){
        int[] nums = {3,2,1,6,0,5};
        System.out.println(Arrays.toString(parentIndexes1(nums)));
    }
    public int[] parentIndexes1(int[] nums){
        int[] lis = new int[nums.length];
        int[] ris = new int[nums.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < nums.length; i++) {
            ris[i] = -1;
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]){
                ris[stack.pop()] = i;
            }
            lis[i] =stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        int[] pis = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (ris[i] == -1 && lis[i] == -1){
                pis[i] = -1;
                continue;
            }
            if (ris[i] == -1){
                pis[i] = lis[i];
            }else if (lis[i] == -1){
                pis[i] = ris[i];
            }else if (nums[ris[i]] < nums[lis[i]]){
                pis[i] = ris[i];
            }else {
                pis[i] = lis[i];
            }

        }
        return pis;
    }
}
