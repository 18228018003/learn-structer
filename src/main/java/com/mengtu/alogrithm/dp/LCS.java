package com.mengtu.alogrithm.dp;

//最长公共子序列
public class LCS {
    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        System.out.println(lengthOfLCS(new int[]{1,3,5,9,10,14,15,17,19,23,25,27,29,30,31,32,33,34},new int[]{1,4,9,10,15,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34}));
        long l1 = System.currentTimeMillis();
        System.out.println((l1-l) + "ms");
        System.out.println(lengthOfLCS1(new int[]{1,3,5,9,10,14,15,17,19,23,25,27,29,30,31,32,33,34},new int[]{1,4,9,10,15,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34}));
        System.out.println((System.currentTimeMillis()-l1) + "ms");
    }

    private static int lengthOfLCS(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0) return 0;
        if (nums2 == null || nums2.length == 0) return 0;
        int[][] dp = new int[nums1.length+1][nums2.length+1];
        for (int i = 1; i <= nums1.length; i++) {
            for (int j = 1; j <= nums2.length; j++) {
                if (nums1[i-1] == nums2[j-1]){
                    dp[i][j] = dp[i-1][j-1]+1;
                }else {
                    dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return dp[nums1.length][nums2.length];
    }

    private static int lengthOfLCS1(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0) return 0;
        if (nums2 == null || nums2.length == 0) return 0;
        return lengthOfLCS1(nums1,nums1.length,nums2,nums2.length);
    }

    private static int lengthOfLCS1(int[] nums1, int i, int[] nums2, int j) {
        if (i == 0 || j == 0) return 0;
        if (nums1[i-1] == nums2[j - 1]) return lengthOfLCS1(nums1, i-1, nums2, j - 1) + 1;
        return Math.max(lengthOfLCS1(nums1, i - 1, nums2, j),lengthOfLCS1(nums1, i, nums2, j-1));
    }


}
