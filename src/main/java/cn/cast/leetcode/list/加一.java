package cn.cast.leetcode.list;

import java.util.Arrays;

/**
 * description
 *
 * @author 周德永
 * @date 2021/12/21 20:11
 */
public class 加一 {
    public static void main(String[] args) {
        int[] digits = {9,9,8,9};
        System.out.println(Arrays.toString(plusOne(digits)));
    }
    public static int[] plusOne(int[] digits) {
        int length = digits.length;
        for (int i = length-1; i >= 0 ; i--) {
            if (digits[i] != 9){
                digits[i]++;
                return digits;
            }else {
                digits[i] = 0;
            }
        }
        int[] arr = new int[length+1];
        arr[0] = 1;
        return arr;
    }
}
