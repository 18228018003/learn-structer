package cn.cast.leetcode.list;

import java.util.Arrays;

/**
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 *  必须在原数组上操作，不能拷贝额外的数组。
 *  尽量减少操作次数。
 * @author 周德永
 * @date 2021/12/21 20:18
 */
public class 移动零 {
    public static void main(String[] args) {
        int[] nums = {0,1,0,3,0,1,6,3,0,12};
        moveZeroes(nums);
        System.out.println(Arrays.toString(nums));
    }
    public static void moveZeroes(int[] nums) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0){
                nums[index++] = nums[i];
            }
        }
        while (index < nums.length){
            nums[index++] = 0;
        }
    }
}
