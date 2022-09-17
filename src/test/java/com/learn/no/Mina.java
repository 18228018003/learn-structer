package com.learn.no;

import org.junit.Test;

/**
 * description
 *
 * @author 周德永
 * @date 2022/2/22 22:47
 */
public class Mina {
    @Test
    public void mai2n() {
        int[] nums = {2, 3, 1, 0, 2, 5, 3};
        System.out.println(findRepeatNumber(nums));
    }
    public int findRepeatNumber(int[] nums) {
        int i = 0;
        while(i < nums.length) {
            if (i == nums[i]) {
                i++;
            } else {
                if (nums[i] == nums[nums[i]]) {
                    return nums[i];
                }
                swap(nums, i, nums[i]);
            }
        }
        return -1;
    }

    public void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }

    public static void main(String[] args) {
        System.out.println("1213");
        System.out.println("2101");

        System.out.println("932");
    }
}
