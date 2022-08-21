package com.zdy.learn.sort;

import java.util.Arrays;

/**
 *  选择排序
 * @author 周德永
 * @date 2021/10/24 15:10
 */
public class SelectSort {
    public static void main(String[] args) {
        int[] arr = {5,4,2,1,7};
        selectSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void selectSort(int[] arr)
    {
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            int min = arr[i];
            for (int j = i+1; j < arr.length; j++) {
                if (arr[j] < min)
                {
                    min = arr[j];
                    minIndex = j;
                }
            }
            if (min != arr[i])
            {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }
}
