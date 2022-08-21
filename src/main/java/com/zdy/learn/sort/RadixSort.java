package com.zdy.learn.sort;

/**
 *  基数排序
 * @author 周德永
 * @date 2021/10/26 23:29
 */
public class RadixSort {
    public static void radixSort(int[] arr){
        if (arr==null ||arr.length<2){
            return;
        }
        radixSort(arr,0,arr.length-1,maxbits(arr));
    }
    public static void radixSort(int[] arr, int l, int r, int digit){
        final int radix = 10;
        int i = 0,j = 0;
        /*有多少数准备多大空间*/
        int[] bucket  = new int[r-l+1];
        for (int k = 1; k <= digit; k++) { /*有多少位进出几次*/
            /*10个空间*/
            /*count[0] 当前位（d位）是0的数字有多少个*/
            /*count[1] 当前位（d位）是0 1的数字有多少个*/
            /*count[2] 当前位（d位）是0 1 2的数字有多少个*/
            /*count[i] 当前位（d位）是0-i的数字有多少个*/
            int[] count = new int[radix];
            for (i = l; i <= r ; i++) {
                j = getDigit(arr[i],k);
                count[j]++;
            }
            for (i = 1; i < radix; i++) {
                j = getDigit(arr[i],k);
                bucket[count[j]-1] = arr[i];
                count[j]--;
            }
            for (i = l, j =0; i<=r; i++,j++) {
                arr[i] = bucket[j];
            }
        }
    }

    private static int getDigit(int i, int k) {
        return 0;
    }

    private static int maxbits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max,arr[i]);
        }
        int res = 0;
        while (max != 0){
            res++;
            max /= 10;
        }
        return res;
    }

}

