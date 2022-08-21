package com.zdy.learn.sort;

import java.util.Arrays;

/**
 *  插入排序
 *
 * @author 周德永
 * @date 2021/10/24 15:25
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] arr = {5,4,2,1,7};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
    public static void sort(int[] a)
    {
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0 && a[j-1] > a[j]; j--) {
                int temp = a[j];
                a[j] = a[j-1];
                a[j-1] = temp;
            }
        }
    }

}
