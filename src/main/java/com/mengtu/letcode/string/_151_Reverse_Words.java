package com.mengtu.letcode.string;

import org.junit.Test;

//逐个翻转字符串中的每个单词
public class _151_Reverse_Words {
    public String reverseWords(String s){

        if (s == null) return null;

        char[] chars = s.toCharArray();
        int length = chars.length;
        //字符串最终的有效长度
        int len = 0;
        //当前存放字符的位置
        int cur = 0;
        //前一个字符是否是空格
        boolean space = true;
        //消除多余空格
        for (int i = 0; i < length; i++) {
            if (chars[i] != ' '){//非空格字符
                chars[cur++] = chars[i];
                len++;
                space = false;
            }else if (!space){//空格字符
                chars[cur++] = ' ';
                len++;
                space = true;
            }
        }
        //翻转
        len = space ? (cur - 1) : cur;
        reverse(chars,0,len);

        //对每一个单词进行逆序
        int preSpaceIdx = -1;
        for (int i = 0; i < len; i++) {
            if (chars[i] != ' ') continue;
            //i 是空格字符的位置
            reverse(chars,preSpaceIdx + 1,i);
            preSpaceIdx = i;
        }
        reverse(chars,preSpaceIdx+1,len);
        return new String(chars,0,len);
    }

    /**
     * 将[L R)内的字符串进行逆序
     */
    private void reverse(char[] chars, int l, int r){
        r--;
        while (l < r){
            char temp = chars[l];
            chars[l] = chars[r];
            chars[r] = temp;
            l++;
            r--;
        }
    }

    @Test
    public void testM(){
        String str = "hello zhou de yong zhang li juan is pag";
        System.out.println(reverseString(str));
    }

    public String reverseString(String s){
        if (s == null || s.length() == 0) return "";
        char[] chars = s.toCharArray();
        int length = chars.length;
        int cur = 0;
        int len = 0;
        boolean space = true;
        for (int i = 0; i < length; i++) {
            if (chars[i] != ' '){
                chars[cur++] = chars[i];
                space = false;
            }else if (!space){
                chars[cur++] = chars[i];
                space = true;
            }
        }
        len = space ? (cur - 1) : cur;
        revers(chars,0,len);
        int spaceIdx = -1;
        for (int i = 0; i < len; i++) {
            if (chars[i] != ' ') continue;
            revers(chars,spaceIdx + 1,i);
            spaceIdx = i;
        }
        revers(chars,spaceIdx+1,len);
        return new String(chars,0,len);
    }
    private void revers(char[] chars,int l, int r){
        r--;
        while (l < r){
            char temp = chars[l];
            chars[l] = chars[r];
            chars[r] = temp;
            l++;
            r--;
        }
    }
}
