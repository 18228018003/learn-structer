package cn.cast.jvm;

import java.util.Arrays;
import java.util.Scanner;

public class MainTest {
    public static void main(String[] args) {
        order();
    }

        public static void ma32in2(String[] args) {
            Scanner scanner = new Scanner(System.in);
            int[] nums = new int[10];
            int size = 0;// 表示有效数据的数量
            for (int i = 0; i < nums.length; i++) {
                int num = scanner.nextInt();
                int position = size; // 新数据插入的索引位置
                for (int j = 0; j < size; j++) {
                    if (nums[j] > num) { // 找到第一个比输入数据大的
                        position = j;
                        break;
                    }
                }
                // 将插入位置开始的数据往后移动
                for (int j = size - 1; j >= position; j--) {
                    nums[j + 1] = nums[j];
                }
                // 将新的数据插入对应位置
                nums[position] = num;
                size++;

                for (int j = 0; j < nums.length; j++) {
                    System.out.print(nums[j]+" ");
                }
                System.out.println("------------------------------");
            }
            for (int i = 0; i < nums.length; i++) {
                System.out.print(nums[i]+ " ");
            }
        }

    private static void order(){
        Scanner scanner = new Scanner(System.in);
        int[] nums = new int[10];
        int size = 0;// 表示有效数据的数量
        for (int i = 0; i < 10; i++) {
            int num = scanner.nextInt();
            int position = size;
            for (int j = 0; j < size; j++) {
                if (nums[j] > num){
                    position = j;
                    break;
                }
            }
            for (int j = size; j > position ; j--) {
                nums[j] = nums[j-1];
            }
            nums[position] = num;
            size++;
            for (int j = 0; j < nums.length; j++) {
                System.out.print(nums[j]+" ");
            }
            System.out.println("------------------------------");
        }

    }

    private static void method(int[] arr) {
        int length = arr.length;
        int[] otherArr = new int[length];
        /*算出奇数索引个数 无论数组长度为多少都是数组长度的一半*/
        for (int i = 0; i < length; i++) {
            if (i % 2== 0){
                otherArr[arr.length/2+i/2] = arr[i];
            }else {
                otherArr[i/2] = arr[i];
            }
        }
        System.out.println(Arrays.toString(otherArr));
    }

}
