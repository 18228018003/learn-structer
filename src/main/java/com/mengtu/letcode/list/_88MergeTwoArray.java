package com.mengtu.letcode.list;

import java.util.Arrays;

public class _88MergeTwoArray {
    public static void merge(int[] nums1,int m,int[] num2,int n){
//        int[] n1 = {1,2,3,0,0,0};
//        int[] n2 = {2,5,6};
         int i1 = m-1;
         int i2 = n-1;
         int cur = nums1.length - 1;
         while (i2 >= 0 && i1 >= 0){
             nums1[cur--] = nums1[i1] > num2[i2] ? nums1[i1--] : num2[i2--];
         }
         while (i2 >= 0)
            nums1[cur--] = num2[i2--];

         while (i1 >= 0)
             nums1[cur--] = nums1[i1--];

        System.out.println(Arrays.toString(nums1));
    }

    public static void main(String[] args) {
        int[] n1 = {1,2,3,0,0,0};
        int[] n2 = {2,5,6};
        merge(n1,3,n2,n2.length);
    }
}
