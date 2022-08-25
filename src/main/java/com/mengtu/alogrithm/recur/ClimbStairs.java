package com.mengtu.alogrithm.recur;

/**
 * 爬楼梯  一共有n阶台阶 每次可以有走一阶或两阶 问总共可以有多少种走法
 */
public class ClimbStairs {
    public static int f(int n){
        if (n == 1) return 1;
        if (n == 2) return 2;
        return f(n - 1) + f(n - 2);
    }
    public int f2(int n){
        if (n == 1) return 1;
        if (n == 2) return 2;
        int[] arr = new int[n + 1];
        arr[1] = 1;
        arr[2] = 2;
        return f2(n, arr);
    }
    public int f2(int n,int[] arr){

        for (int i = 3; i <= n; i++) {
            arr[i] = f2(i-1,arr) + f2(i-2,arr);
        }
        return arr[n];
    }
    public static int f1(int n){
        int[] arr = new int[n + 1];
        arr[1] = 1;
        arr[2] = 2;
        for (int i = 3; i <= n; i++) {
            arr[i] = arr[i-1] + arr[i-2];
        }
        return arr[n];
    }
    public static int f3(int n){
        int first = 1;
        int second = 2;
        for (int i = 3; i <= n; i++) {
            second = first + second;
            first = second - first;
        }
        return second;
    }
    public static void main(String[] args) {
        ClimbStairs c = new ClimbStairs();
        System.out.println(f(19));
        System.out.println(f1(19));
        System.out.println(c.f2(19));
        System.out.println(f3(19));
    }
}
