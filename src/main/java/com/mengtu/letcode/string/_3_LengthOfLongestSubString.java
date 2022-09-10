package com.mengtu.letcode.string;

import java.util.HashMap;
import java.util.Map;

/**
 * 无重复字符的最长子串
 */
public class _3_LengthOfLongestSubString {
    public int lengthOfLongestSubString(String s){
        if (s == null) return 0;
        char[] chars = s.toCharArray();
        //用来保存每一个字符上一次出现的位置
        Map<Character,Integer> preIdxes = new HashMap<>();
        preIdxes.put(chars[0],0);
        //以i-1位置字符结尾的最长不重复字符串的开始索引
        int li = 0;
        for (int i = 1; i < chars.length; i++) {
            //i位置字符上一次出现的位置
            Integer pi = preIdxes.get(chars[i]);
            if (li <= pi){
                li = pi + 1;
            }
            preIdxes.put(chars[i],i);
        }
        return 0;
    }
}
