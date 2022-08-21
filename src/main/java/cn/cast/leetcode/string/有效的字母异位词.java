package cn.cast.leetcode.string;

import java.util.Arrays;

/**
 * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
 *
 * 注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
 *  输入: s = "anagram", t = "nagaram"
 *  输出: true
 * @author 周德永
 * @date 2021/12/23 23:10
 */
public class 有效的字母异位词 {
    public static void main(String[] args) {

    }
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] letterCount = new int[26];
        //统计字符串s中的每个字符的数量
        for (int i = 0; i < s.length(); i++) {
            letterCount[s.charAt(i)-'a']++;
        }
        //如果数组letterCount的每个值都是0，返回true，否则返回false
        for (int i = 0; i < t.length(); i++) {
            //如果当前字符等于0，直接返回false，
            if (letterCount[t.charAt(i) - 'a'] == 0) return false;
            letterCount[t.charAt(i)-'a']--;
        }
        for (int i : letterCount) {
            if (i != 0){
                return false;
            }
        }
        return true;
    }
    public boolean isAnagram2(String s, String t) {
        char[] c1 = s.toCharArray();
        char[] c2 = t.toCharArray();
        Arrays.sort(c1);
        Arrays.sort(c2);
        return Arrays.equals(c1,c2);
    }
}
