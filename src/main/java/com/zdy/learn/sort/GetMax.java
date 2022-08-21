package com.zdy.learn.sort;

/**
 * 递归返回最大值
 * @author 周德永
 * @date 2021/10/24 17:25
 */
public class GetMax {
    public static int getMax(int[] arr){
        return process(arr,0,arr.length-1);
    }

    private static int process(int[] arr, int L, int R) {
        if (L==R){
            return arr[L];
        }
        int mid = L + (R-L)>>2;
        int leftMax = process(arr,L,mid);
        int rightMax = process(arr,mid+1,R);
        return Math.max(leftMax,rightMax);
    }
}
