package cn.cast.leetcode.string;

import java.util.Arrays;

/**
 * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
 *
 * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
 *
 * 示例 1：
 * 输入：s = ["h","e","l","l","o"]
 * 输出：["o","l","l","e","h"]
 *
 * 示例 2：
 * 输入：s = ["H","a","n","n","a","h"]
 * 输出：["h","a","n","n","a","H"]
 * @author 周德永
 * @date 2021/12/21 21:48
 */
public class 反转字符串 {
    public static void main(String[] args) {
        String str = "hello,world";
        System.out.println(reverseStr(str.toCharArray()));

    }
    public static char[] reverseStr(char[] chars){
        int left = 0;
        int right = chars.length-1;
        while (left < right){
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        return chars;
    }









    public static void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left < right){
            swap(s,left++,right--);
        }
        System.out.println(Arrays.toString(s));
    }

    private static void swap(char[] s, int i, int i1) {
        char temp = s[i];
        s[i] = s[i1];
        s[i1] = temp;
    }
}
