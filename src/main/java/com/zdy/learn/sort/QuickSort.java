package com.zdy.learn.sort;

import org.junit.Test;

/**
 *  快速排序
 *  荷兰国旗问题：
 *  给定一个数组和一个指定的数num
 *      小于该数的在左侧，等于该数的中间，大于该数的在右侧
 *   思路： 指针变量i。
 *   如果此数小于num,和小于区域的下一个数交换,i++,小于区域右移动一位。
 *   如果等于num,i++;
 *   如果大于num,和大于区域的前一个数交换，i不变。大于区域前移。
 *
 * @author 周德永
 * @date 2021/10/24 22:03
 */
public class QuickSort {
    @Test
    public void test(){

    }
    public static void quickSort(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        quick(arr,0,arr.length-1);
    }

    private static void quick(int[] arr, int l, int r) {
        if (l < r){
            swap(arr,l+(int)(Math.random()*(r-l+1)),r);
            int[] p = partition(arr,l,r);
            quick(arr,l,p[0] - 1);
            quick(arr,p[1]+1,r);
        }
    }
    /*处理arr[l...r]的函数
    * 默认以arr[r]做划分，arr[r] -> p  <p ==p >p*/
    private static int[] partition(int[] arr, int l, int r) {
        int less = l - 1; //小于区域的右边界。
        int more = r; //大于区域的左边界
        while (l < more){//l表示当前数的位置 arr[r] -> 划分值
            if (arr[l] < arr[r]){
                swap(arr, ++less, l++);
            }else if (arr[l] > arr[r]){
                swap(arr, --more, l);
            }else {
                l++;
            }
        }
        swap(arr, more, r);
        return new int[]{ less + 1, more };
    }

    private static void swap(int[] arr, int l, int r) {
        int temp = arr[l];
        arr[l] = arr[r];
        arr[r] = temp;
    }
}
