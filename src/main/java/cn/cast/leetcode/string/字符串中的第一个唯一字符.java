package cn.cast.leetcode.string;


import java.util.HashMap;
import java.util.Map;

/**
 * description
 *
 * @author 周德永
 * @date 2021/12/23 22:35
 */
public class 字符串中的第一个唯一字符 {
    public static void main(String[] args) {
        System.out.println(firstUniqChar("loceleetcode"));
    }
    public static int firstUniqChar(String s) {
        Map<Character, Integer> map = new HashMap<>();
        char[] chars = s.toCharArray();
        //先统计每个字符的数量
        for (char ch : chars) {
            map.put(ch,map.getOrDefault(ch,0)+1);
        }
        for (int i = 0; i < s.length(); i++) {
            if (map.get(chars[i])==1){
                return i;
            }
        }
        return -1;
    }
}
