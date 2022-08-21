package com.zdy.learn.sort;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 *  堆排序
 *   扩展：已知一个几乎有序的数组，几乎有序是指，如果把数组排好顺序的话，
 *   每个元素移动的距离不可以超过k，并且k对于数组来说较小。选择一个合适的算法来排序
 * @author 周德永
 * @date 2021/10/26 0:04
 */
public class HeapSort {





    public static void main(String[] args) {
        int[] arr = {5,4,2,1,7};
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void sortArrayDistanceLessK(int[] arr, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int index = 0;
        for (; index <= Math.min(arr.length,k); index++){
            heap.add(arr[index]);
        }
        int i = 0;
        for (; index < arr.length; i++,index++){
            heap.add(arr[index]);
            arr[i] = heap.poll();
        }
        while (!heap.isEmpty()){
            arr[i++] = heap.poll();
        }
    }

    private static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2){
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            heapInsert(arr,i);
        }
        int heapSize = arr.length;
        swap(arr,0,--heapSize);

        while (heapSize > 0){
            heapify(arr,0,heapSize);
            swap(arr,0,--heapSize);
        }
    }

    /*将数组中的元素排成大根堆结构*/
    public static void heapInsert(int[] arr, int index){
        while (arr[index] > arr[(index -1) /2]){
            swap(arr,index,(index - 1)/2);
            index  = (index - 1)/2;
        }
    }

    /*堆化*/
    public static void heapify(int[] arr, int index, int heapSize){
        int left = index * 2 + 1; /*左孩子下标*/
        while (left < heapSize){/*左方还有孩子节点*/
            /*两个孩子中，谁的值大，把下标给largest*/
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            /*父节点和孩子节点之间 谁的值大*/
            largest = arr[index] > arr[largest] ? index : largest;
            if (largest == index) break;
            swap(arr,largest,index);
            index = largest;
            left = index * 2 + 1;
        }
    }


    private static void swap(int[] arr, int index, int i) {
        int temp = arr[index];
        arr[index] = arr[i];
        arr[i] = temp;
    }


}
