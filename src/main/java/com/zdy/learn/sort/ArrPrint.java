package com.zdy.learn.sort;

/**
 *
 * @author 周德永
 * @date 2021/10/24 16:44
 */
public class ArrPrint {
    public static void main(String[] args) {
        int[] arr = {1,2,2,1,2,3,3,3,3,4,4,5,5,77};
        //print(arr);
        printTwice(arr);
    }

//    给定一个数组，其中只有一个数只会出现奇数次，其余都出现偶数次，要求打印这个奇数次的值
    private static void print(int[] arr) {
        int eor = 0;
        for (int i : arr) {
            eor ^= i;
        }
        System.out.println(eor);
    }
//    给定一个数组，其中只有两个数个数只会出现奇数次，其余都出现偶数次，要求打印这个奇数次的值
    private static void printTwice(int[] arr) {
        int eor = 0;
        for (int i : arr) {
            eor ^= i; // eor = a^b
        }
        int rightOne = eor & (~eor +1); //取出eor最右侧的1
        int onlyOne = 0; //eor'
        for (int a : arr) {
            if ((a & rightOne) == 0) {
                onlyOne ^= a;
            }
        }


        System.out.println(onlyOne + " "+(onlyOne ^ eor));
    }
}
