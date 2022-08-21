package com.zdy.learn.tanxin;

import java.util.PriorityQueue;

/**
 *
 *
 * @author 周德永
 * @date 2021/10/31 22:34
 */
public class LessMoneySplitGold {
    public static int lessMoney(int[] arr){
        /*小根堆*/
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int num : arr) {
            priorityQueue.add(num);
        }
        int sum = 0;
        int cur;
        while (priorityQueue.size() > 1){
            cur = priorityQueue.poll() + priorityQueue.poll();
            sum += cur;
            priorityQueue.add(cur);
        }
        return sum;
    }

    public static void main(String[] args) {
        int[] arr = {10,20,30,40};
        System.out.println(lessMoney(arr));
    }

}
