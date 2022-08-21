package cn.cast.recur;

import cn.cast.list.stack.Stack;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 将一个栈逆序 不许使用额外的空间
 */
public class ReverseStack {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.push(6 );
        reverse(stack);
        while (!stack.isEmpty()){
            System.out.println(stack.pop());
        }
    }
    @Test
    public void process(){
        String s = "abc";
        char[] str = s.toCharArray();
        List<String> ans = new ArrayList<>();
        String path = "";
        //process1(str,0,ans,path);
        process2(str,0,ans);
        System.out.println(ans);
    }
    private void process2(char[] str,int index,List<String> ans){
        if(index == str.length){
            ans.add(String.valueOf(str));
            return;
        }
        for (int i = index; i < str.length; i++) {
            swap(str,i,index);
            process2(str,index+1,ans);
            swap(str,i,index);
        }

    }
    public int process(char[] str,int i){
        if (i == str.length) return 1;
        if (str[i] == '0'){
            return 0;
        }
        if (str[i] == '1'){
            int res = process(str,i+1);
            if (i+1 < str.length){
                res += process(str,i+2);
            }
            return res;
        }
        if (str[i] == '2') {
            int res = process(str,i+1);
            if (i+1 < str.length && (str[i+1] >= '0' && str[i+1] <= '6')){
                res+=process(str,i+2);
            }
            return res;
        }
        return process(str,i+1);
    }
    private void swap(char[] str, int i, int index) {
        char temp = str[i];
        str[i] = str[index];
        str[index] = temp;
    }

    private void process1(char[] str, int index, List<String> ans, String path) {
        if (index == str.length){
            ans.add(path);
            return;
        }
        String no = path;
        process1(str,index+1,ans,no);
        String yes = path + str[index];
        process1(str,index+1,ans,yes);
    }

    public static int getButtom(Stack<Integer> stack){
        int result = stack.pop();
        if (stack.isEmpty()){
            return result;
        }else {
            int last = getButtom(stack);
            stack.push(result);
            return last;
        }
    }

    public static void reverse(Stack<Integer> stack){
        if (stack.isEmpty()) return;
        int i = getButtom(stack);
        reverse(stack);
        stack.push(i);

    }
}
