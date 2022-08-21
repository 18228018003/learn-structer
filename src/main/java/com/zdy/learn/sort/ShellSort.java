package com.zdy.learn.sort;

import java.util.Arrays;

/**
 *  希尔排序
 * @author 周德永
 * @date 2021/10/24 16:02
 */
public class ShellSort {
    public static void main(String[] args) {
        int[] arr = {5,4,2,1,7};
        shell(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void shell(int[] arr) {
        /*根据数组arr 的长度 确定初始长度*/
        int h = 1;
        while (h<arr.length) {
            h = h*2+1;
        }
        /*希尔排序*/
        while ( h >= 1) {
            /*排序*/
            for (int i = h; i < arr.length-1; i++) {
                for (int j = i; j >= h; j-=h) {
                    if(arr[j] > arr[j-h]) {
                        int temp = arr[j];
                        arr[j] = arr[j-h];
                        arr[j-h] = temp;
                    }
                    else{
                        break;
                    }
                }
            }
            h=h/2;
        }
    }

}
