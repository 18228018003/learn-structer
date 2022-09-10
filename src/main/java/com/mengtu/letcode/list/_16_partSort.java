package com.mengtu.letcode.list;

import org.junit.Test;

import java.util.Arrays;

//部分排序
public class _16_partSort {
    /*
    * {1,5,4,3,2,0,8,79,80}
    * */
    public int[] subSort(int[] nums){
        //从左扫描到右寻找逆序对（正序：逐渐变大）
        int max = nums[0];
        //用来记录最右的那个逆序对位置
        int right = -1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] >= max){
                max = nums[i];
            }else {
                right = i;
            }
        }

        int min = nums[nums.length-1];
        int left = -1;
        for (int i = nums.length-2; i >= 0; i--) {
            if (nums[i] < min){
                min = nums[i];
            }else {
                left = i;
            }
        }

        return new int[]{left,right};
    }

    @Test
    public void testSubSort(){
        int[] arr = {1,5,4,3,2,0,8,79,80};
        int[] ints = subSort(arr);
        System.out.println(Arrays.toString(ints));
    }


}
