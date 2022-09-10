package com.mengtu.letcode.string;

public class _242_stringCount {
    public boolean isAnagram(String s,String t){
        if (s == null || t == null) return false;
        if (s.length() != t.length()) return false;

        int[] counts = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counts[s.charAt(i)-'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            if (--counts[t.charAt(i)-'a'] < 0) return false;
        }

        return true;
    }
}
