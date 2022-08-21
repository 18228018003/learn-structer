package com.zdy.learn.sort;

import java.util.Arrays;

/**
 *  冒泡排序
 * @author 周德永
 * @date 2021/10/24 15:06
 */
public class BubbleSort
{
    public static void main(String[] args)
    {
        int[] arr = {5,4,2,1,7};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }
    public static void bubbleSort(int[] arr)
    {
        boolean flag = true;
        for (int i = 0; i < arr.length-1; i++)
        {
            for (int j = 0; j < arr.length-i-1; j++)
            {
                if (arr[j] > arr[j+1])
                {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    flag = false;
                }
            }
            if (flag)
            {
                break;
            }
        }
    }
}
