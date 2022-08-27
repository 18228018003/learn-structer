package com.mengtu.alogrithm.divide;

public class Main {
    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        //最大连续子序列的和
        System.out.println(maxSubArray(nums));
    }

    private static int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        return maxSubArray(nums,0, nums.length);
    }
    private static int maxSubArray(int[] nums,int begin,int end) {
        if (end - begin < 2) return nums[begin];
        int mid = (end + begin) >> 1;
        int leftMax = 0;
        int rightMax = 0;
        int sum = 0;
        for (int i = mid - 1; i >= begin; i--) {
            sum += nums[i];
            leftMax = Math.max(leftMax,sum);
        }
        sum = 0;
        for (int i = mid; i < end; i++) {
            sum += nums[i];
            rightMax = Math.max(rightMax,sum);
        }

        int max = leftMax + rightMax;
        return Math.max(max,Math.max(maxSubArray(nums, begin, mid),maxSubArray(nums,mid,end)));
    }

    static int maxSubArray1(int[] nums){
        if (nums == null || nums.length == 0) return 0;
        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            for (int end = begin; end < nums.length; end++) {
                int sum = 0;
                for (int i = begin; i <= end; i++) {
                    sum += nums[i];
                }
                max = Math.max(max,sum);
            }
        }

        return max;
    }
    static int maxSubArray2(int[] nums){
        if (nums == null || nums.length == 0) return 0;
        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            int sum = 0;
            for (int end = begin; end < nums.length; end++) {
                sum += nums[end];
                max = Math.max(max,sum);
            }
        }

        return max;
    }
}
