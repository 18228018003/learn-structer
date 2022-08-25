package com.mengtu.alogrithm.recur;

/**
 * 斐波那契
 */
public class Fib {
    //n项和递归解法
    //f(n) = f(n-1)+1;
    public int sum(int n){
        if (n == 1) return 1;
        return n + sum(n-1);
    }
    //斐波那契数
    //f(n) = f(n-1) + f(n-2);
    public int fib(int n){
        if (n <= 2) return 1;
        return fib(n-1)+fib(n-2);
    }
    //优化斐波那契函数第一步
    public int fib1(int n){
        int[] arr = new int[n+1];
         if (n <= 2) return 1;
         arr[1] = arr[2] = 1;
        return fib1(n,arr);
    }

    //优化1 使用数组保存计算过的值的变量。
    private int fib1(int n,int[] arr){
//        arr[n] = n;
        if (arr[n] == 0){
            arr[n] = fib1(n-1,arr)+fib1(n-2,arr);
        }
        return arr[n];
    }

    //优化二 去除递归  空间复杂度还是没变 自下而上
    public int fib2(int n){
        if (n <= 2) return 1;
        int[] arr = new int[n+1];
        arr[1] = arr[2] = 1;
        for (int i = 3; i <= n; i++) {
            arr[i] = arr[i-1]+arr[i-2];
        }
        return arr[n];
    }
    //优化三 ，减少空间复杂度 使用滚动数组
    public int fib3(int n){
        if (n <= 2) return 1;
        int[] arr = new int[2];
        arr[0] = arr[1] = 1;
        for (int i = 3; i <= n; i++) {
            arr[i%2] = arr[(i-1)%2] + arr[(i-2)%2];
        }
        return arr[n % 2];
    }
    public int fib4(int n){
        if (n <= 2) return 1;
        int first = 1;
        int second = 1;
        for (int i = 3; i <= n; i++) {
            second = first + second;
            first = second - first;
        }
        return second;
    }
    public int fib5(int n){
        double c = Math.sqrt(5);
        return (int)((Math.pow(((1 + c) / 2),n) - Math.pow((1 - c) / 2, n) / c));
    }
    public static void main(String[] args) {
        Fib fib = new Fib();
        System.out.println(fib.fib1(40));
//        System.out.println(fib.fib(10));
        System.out.println(fib.fib2(40));
        System.out.println(fib.fib3(40));
    }
}
