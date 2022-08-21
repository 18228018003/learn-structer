package cn.cast.recur;

import org.junit.Test;

/*斐波那契*/
public class Fibonacc {

    /*暴力递归*/
    public int f1(int n){
        if (n < 0) return -1;
        if(n==1 || n == 2) return 1;
        return f1(n-1) + f1(n-2);
    }

    /*优化 减少重复计算*/
    public int f2(int n){
        if (n < 0) return -1;
        int[] array = new int[n + 1];
        array[1] = array[2] = 1;
        return f2(array,n);
    }

    private int f2(int[] array, int n) {

        if (array[n] == 0){
            array[n] = f2(array,n-1) + f2(array,n-2);
        }
        return array[n];
    }
    /*优化 去除递归调用*/
    public int f3(int n){
        if (n < 0) return -1;
        int[] array = new int[n + 1];
        array[1] = array[2] = 1;
        return f3(array,n);
    }
    private int f3(int[] array, int n) {
        for (int i = 3; i <= n; i++) {
            array[i] = array[i-1]+array[i-2];
        }
        return array[n];
    }
    public int f4(int n){
        int x = 1;
        int y = 1;
        int sum = 0;
        for (int i = 3; i <= n ; i++) {
            sum = x + y;
            x = y;
            y = sum;
        }
        return sum;
    }
    public int f5(int n){
        int[] arr = new int[2];
        arr[0] = arr[1] = 1;
        for (int i = 3; i <= n ; i++) {
            arr[i&1] = arr[(i-1)&1] + arr[(i-2)&1];
        }
        return arr[n&1];
    }
    @Test
    public void tes12t(){
//        System.out.println(f1(100));
//        System.out.println(f2(10));
        int n = 44;
        System.out.println(f3(n));
        System.out.println(f4(n));
        System.out.println(f5(n));
    }
}
