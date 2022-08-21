package cn.cast.leetcode.string;

/**
 * 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
 *  输入：s = "We are happy."
 *  输出："We%20are%20happy."
 * @author 周德永
 * @date 2022/1/25 16:19
 */
public class 剑指Offer05替换空格 {
    public static void main(String[] args) {
        String str = "We are happy.";
        System.out.println(replaceSpace(str));
    }
    public static String replaceSpace(String s) {
        char[] chars = s.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != ' '){
                builder.append(chars[i]);
            }else {
                builder.append("%20");
            }
        }
        return builder.toString();
    }
}
