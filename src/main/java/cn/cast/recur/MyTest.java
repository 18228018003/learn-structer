package cn.cast.recur;

import org.junit.Test;

/**
 * description
 *
 * @author zhoudy
 * @date 2021/12/13 0:04
 */
public class MyTest {
    @Test
    public void test(){
        int n = 10;
        int x = 1;
        int y = 1;
        int sum = 0;
        for (int i = 3; i <= n; i++) {
            sum = x + y;
                x = y;
                y = sum;
        }
        System.out.println(sum);
    }
    public void test1(int n){
        if (n <= 0)return;
        System.out.println(n);
        test1(n-1);
    }
}
