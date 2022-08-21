package com.zdy.learn.fibonacce;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/19 0:01
 */
public class Test {
    public static int fib(int n){
        if (n <= 1) return n;
        return fib(n-1)+fib(n-2);
    }
    public static long fib2(int n){
        if (n <= 1) return n;
        long first = 0;
        long second = 1;
        long sum = 0;
        for (int i = 0; i < n-1; i++) {
            sum = first+second;
            first= second;
            second =sum;
        }
        return sum;
    }
    public static void main(String[] args) {
        System.out.print(fib2(1)+" ");
        System.out.print(fib2(2)+" ");
        System.out.print(fib2(3)+" ");
        System.out.print(fib2(4)+" ");
        System.out.print(fib2(5)+" ");
        System.out.print(fib2(6)+" ");
        System.out.print(fib2(7)+" ");
        System.out.print(fib2(8)+" ");
    }
    @org.junit.Test
    public void tes(){
        long l = System.currentTimeMillis();
        System.out.println(fib2(100));
        long l1 = System.currentTimeMillis();
        System.out.println((l1-l)+" ms");
        System.out.println(fib(50));
        long l2 = System.currentTimeMillis();
        System.out.println((l2-l1)+" ms");
    }
}
