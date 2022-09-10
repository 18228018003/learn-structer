package com.mengtu.letcode.stack;

import java.util.Deque;
import java.util.LinkedList;

public class _239_SlidingWindow {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];
        if (k == 1) return nums;
        int[] maxes = new int[nums.length - k + 1];
        for (int i = 0; i < maxes.length; i++) {
            int max = nums[i];
            for (int j = i; j < i + k; j++) {
                if (max < nums[j]) max = nums[j];
            }
            maxes[i] = max;
        }
        return maxes;
    }
    public int[] maxSlidingWindow2(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) return null;
        if (k == 1) return nums;
        int[] maxes = new int[nums.length - k + 1];
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            //nums[队尾] <= nums[i] 就删除队尾
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]){
                deque.pollLast();
            }
            deque.offerLast(i);
            //检查窗口的索引是否合法
            int w = i - k + 1;
            if (w < 0) continue;
            //检查队头合法性
            if (deque.peekFirst() < w){
                //队头不合法（失效，不在滑动窗口索引范围内）
                deque.pollFirst();
            }
            //设置窗口最大值
            maxes[w] = nums[deque.peekFirst()];
        }
        return maxes;
    }

}