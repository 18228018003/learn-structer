package cn.cast.leetcode.list;

import cn.cast.list.map.map.HashMap;

import java.util.Arrays;

/**
 * 给定一个整数数组 nums 和一个整数目标值 target，
 * 请你在该数组中找出 和为目标值 target  的那 两个 整数，
 * 并返回它们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 *
 * 你可以按任意顺序返回答案。
 *
 *
 * @author 周德永
 * @date 2021/12/21 20:34
 */
public class 两数之和 {
    public static void main(String[] args) {
        int[] nums = {2,7,11,15,10};
        int target = 12;
        System.out.println(Arrays.toString(twoSum(nums, target)));
    }
    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.get(target-nums[i]) != null){
                return new int[]{map.get(target-nums[i]),i};
            }
            map.put(nums[i],i);//(2,0)(7,1)(11,2)(15,3)
        }
        return null;
    }
}
