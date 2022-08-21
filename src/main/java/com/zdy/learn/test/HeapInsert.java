package com.zdy.learn.test;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/5 23:47
 */
public class HeapInsert {
    public static void heapSort(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }

    }

    public static void heapify(int[] arr,int index,int heapSize){
        int left = index *2 + 1;
        while (left < heapSize){
            int biggest = left+1 < heapSize && arr[left+1] > arr[left] ? left+1 : left;
            biggest = arr[index] > arr[biggest] ? index : biggest;
            if (biggest == index){
                break;
            }
            swap(arr,index,biggest);
            index = biggest;
            left = index *2 +1;
        }
    }

    public static void heapInsert(int[] arr,int index){
        while (arr[index] > arr[(index-1)/2]){
            swap(arr,index,(index-1)/2);
            index = (index-1)/2;
        }
    }

    private static void swap(int[] arr, int index, int i) {
        int temp = arr[i];
        arr[i] = arr[index];
        arr[index] = temp;
    }

}
