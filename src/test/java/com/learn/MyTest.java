package com.learn;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
class Student{
    byte[] bytes = new byte[1024*1024];
}
public class MyTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Integer> list = new ArrayList<>();
        list.add(0);

        list.getClass().getMethod("add",Object.class).invoke(list,"张丽娟");

        System.out.println(list);
    }

    @Test
    public void heapSize(){
        ArrayList<Student> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add(new Student());
        }
        try {
            Thread.sleep(100000000000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void stream(){
        long t0 = System.nanoTime();
        IntStream.range(0,10_0000_0000).filter(i -> (i & 3) == 0 && (i & 2) == 0).toArray();
        long t1 = System.nanoTime();
        IntStream.range(0,10_0000_0000).parallel().filter(i -> (i & 3) == 0 && (i & 2) == 0).toArray();
        long t2 = System.nanoTime();
        System.out.printf("serial: %.2fs, parallel %.2fs%n", (t1 - t0) * 1e-9, (t2 - t1) * 1e-9);
    }


    @Test
    public void sort(){

        int[] arr = {4,3,2,1};
        for (int i = 0; i < arr.length-1; i++) {// 表示有多少趟 4个数字三趟 3个数字两趟 2个数字一趟
            /*
                4, 3, 2, 1
                外层循环 i = 0
                    内层循环 j = 0;
                        3,4,2,1
                    内层循环 j = 1;
                        3,2,4,1
                    内层循环 j = 2;
                        3,2,1,4
                第二趟 i = 1
                    内层循环 j = 0;
                        2,3,1,4
                    内层循环 j = 1;
                        2,1,3,4

             */
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testSum(){
        int[] arr = {1,2,3,4,5,6,7,8,9,10};
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        System.out.println(sum);
    }
    @Test
    public void getMax(){
        int[][] arr = new int[10][10];
        for (int i = 0; i < arr.length; i++) {
            arr[i][0] = 1;
            arr[1][1] = 1;
            for (int j = 0; j < arr[0].length; j++) {
                if (i > 1 && j > 0 ){
                    arr[i][j] = arr[i-1][j-1] + arr[i-1][j];
                }
                if (arr[i][j] != 0){
                    System.out.print(arr[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
    @Test
    public void testFindNum(){
        int[][] arr = {
                {1,  4,  7,  11, 15},
                {2,  5,  8,  12, 19},
                {3,  6,  9,  16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}};
        System.out.println(find(5,arr));
    }
    public boolean find(int target, int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return false;
        int rows = matrix.length;
        int cols = matrix[0].length;
        int r = 0;
        int c = cols -1;
        while (r <= rows - 1 && c >= 0){
            if (target == matrix[r][c])
                return true;
            else if (target > matrix[r][c])
                r++;
            else
                c--;
        }
        return false;
    }
    @Test
    public void duplicate(){
        int[] nums = {2, 3, 1, 0, 2, 5};
        int len = nums.length;
        int i = 0;
        while(i < len){
            if (nums[i] == i){
                i++;
            }else {
                if (nums[i] == nums[nums[i]])
                {
                    break;
                }
                swap(nums,i,nums[i]);
            }
        }
        System.out.println(Arrays.toString(nums));
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
