package com.mengtu.letcode.string;

//字符串旋转
public class _0109_StringRevolving {

    public static boolean isRevolving(String s1,String s2){
        if (s1 == null || s2 == null) return false;
        if (s1.length() != s2.length()) return false;
        return (s1 + s2).contains(s2);
    }

    public static int[] temp(int[] T){

        int[] values = new int[T.length];
        for (int i = T.length - 2; i >= 0; i--) {
            int j = i + 1;
            while (true){
                if (T[i] < T[j]){
                    values[i] = j - i;
                    break;
                }else if (values[j] == 0){
                    values[i] = 0;
                    break;
                }else if (T[i] == T[j]){
                    values[i] = values[j] + j - i;
                    break;
                }else {
                    j = values[j] + j;
                }
            }
        }
        return values;
    }
}
