package com.zdy.learn.tanxin;

import java.util.Arrays;
import java.util.Comparator;

/**
 *  贪心算法求字典序
 *
 * @author 周德永
 * @date 2021/10/31 22:27
 */
public class LowestLexicography {
    public static class MyComparator implements Comparator<String> {

        @Override
        public int compare(String a, String b) {
            return (a + b).compareTo(b + a);
        }

    }

    public static String lowestString (String[] strs){
        if (strs == null || strs.length == 0){
            return "";
        }
        Arrays.sort(strs,new MyComparator());
        String res = "";
        for (int i = 0; i < strs.length; i++) {
            res += strs[i];
        }
        return res;
    }

    public static void main(String[] args) {
        String[] strs = {"abc","bcx","acx","aby"};
        String s = lowestString(strs);
        System.out.println(s);
    }
}
