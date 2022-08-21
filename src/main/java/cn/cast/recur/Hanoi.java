package cn.cast.recur;

import cn.cast.list.stack.Stack;
import org.junit.Test;

/**
 * description
 *
 * @author zhoudy
 * @date 2021/12/8 23:04
 */
public class Hanoi {
    public static void main(String[] args) {
//        long l = System.currentTimeMillis();
        hanoi(3,"A","B","C");
//        long l2 = System.currentTimeMillis();
//        System.out.println(l2-l + " ms ");
//        System.out.println(fbnc(4));
//        System.out.println(fbnc(5));
//        System.out.println(fbnc(6));
//        System.out.println(fbnc(7));
        System.out.println("================================");
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        reverse(stack);
        while (!stack.isEmpty()){
            System.out.println(stack.pop());
        }
    }
    public static int f(Stack<Integer> stack){
        int result = stack.pop();
        if (stack.isEmpty()){
            return result;
        }else {
            int last = f(stack);
            stack.push(result);
            return last;
        }
    }

    public static void reverse(Stack<Integer> stack){
        if (stack.isEmpty()) {
            return;
        }
        int i = f(stack);
        reverse(stack);
        stack.push(i);
    }

    public static void hanoi(int n,String source,String help,String target){
        if (n == 1){
            System.out.println(n+":"+source+"->"+target);
            return;
        }
        hanoi(n-1,source,target,help);
        System.out.println(n+":"+source+"->"+target);
        hanoi(n-1,help,source,target);
    }

    public static int f(int n){
        if (n<=2){
            return 1;
        }
        return f(n-1)+f(n-2);
    }

    public static int fbnc(int n){
        int fir = 1;
        int sec = 1;
        int sum = 0;
        for (int i = 2; i < n; i++) {
            sum = fir + sec;
            fir = sec;
            sec = sum;
        }
        return sum;
    }
    @Test
    public void sum(){
        System.out.println(multiply(11, 11));
    }
    public int sum(int a,int b){
        int carry = a & b;
        int sum = a ^ b;
        while (carry != 0){
            carry <<= 1;
            int temp = sum;
            sum = sum ^ carry;
            carry = carry & temp;
        }
        return sum;
    }
    public int multiply(int A, int B) {
        if(A==0 || B==0) return 0;
        return f(A,B,0);
    }
    public int f(int a,int b,int sum){
        if(b==1) return sum + a;
        sum = sum + a;
        return f(a,b-1,sum);
    }

}
