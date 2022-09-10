package com.mengtu.letcode.list;

import java.util.Arrays;

//颜色分类
public class _75ColorType {
    public static void main(String[] args) {
        int[] nums = {2,0,1};
        sortColors(nums);
        System.out.println(Arrays.toString(nums));
    }

    private static void sortColors(int[] arr,int right) {
        int less = -1;
        int more = right;
        int left = 0;
        for (int i = 0; i < right; i++) {
            if (arr[i] == 1){
                swap(arr,i, right);
            }
        }
        while (left < more){
            if (arr[left] > arr[right]){
                swap(arr,left,--more);
            }else if (arr[left] < arr[right]){
                swap(arr,++less,left++);
            }else {
                left++;
            }
        }
        swap(arr,more,arr.length-1);
    }
    private static void sortColors(int[] arr) {
        int left = 0;
        int right = arr.length-1;
        int help = 0;
        while (left <= right){
            if (arr[left] == 2){
                swap(arr,left,right--);
            }else if (arr[left] == 0){
                swap(arr,left++,help++);
            }else {
                left++;
            }
        }
    }
    private static void swap(int[] arr, int left, int i) {
        int temp = arr[left];
        arr[left] = arr[i];
        arr[i] = temp;
    }
}
