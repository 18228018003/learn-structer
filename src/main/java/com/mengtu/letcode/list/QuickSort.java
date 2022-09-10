package com.mengtu.letcode.list;

import org.junit.Test;

import java.util.Arrays;

public class QuickSort {

    @Test
    public void testSort(){
        int[] arr = new int[]{64,2,3,5,1,6,7};
        quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public void quickSort(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        sort(arr,0,arr.length-1);
    }

    private void sort(int[] arr, int left, int right) {
        if (left < right){
            swap(arr,right,left + (int)(Math.random() * (right-left+1)));
            int[] p = partition(arr,left,right);
            sort(arr,left,p[0]);
            sort(arr,p[1]+1,right);
        }
    }

    private int[] partition(int[] arr, int left, int right) {
        int less = left - 1;
        int more = right;
        while (left < more){
            if (arr[left] > arr[right]){
                swap(arr,--more,left);
            }else if (arr[left] < arr[right]){
                swap(arr,left++,++less);
            }else {
                left++;
            }
        }
        swap(arr,more,right);

        return new int[]{less+1,more};
    }

    private void swap(int[] arr, int right, int i) {
        int temp = arr[right];
        arr[right] = arr[i];
        arr[i] = temp;
    }
}
