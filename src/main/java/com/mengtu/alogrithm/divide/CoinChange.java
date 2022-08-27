package com.mengtu.alogrithm.divide;

import java.util.Arrays;

//初步动态规划
public class CoinChange {
    public static void main(String[] args) {
        coinChange(new int[]{25,10,5,1},41);
    }

    private static void coinChange(int[] nums, int money) {
        Arrays.sort(nums);
        int coins = 0, idx = nums.length - 1;
        while (idx >= 0){
            while (nums[idx] <= money){
                money-=nums[idx];
                coins++;
            }
            idx--;
        }
        System.out.println(coins);
    }
}
