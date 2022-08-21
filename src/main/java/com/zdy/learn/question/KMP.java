package com.zdy.learn.question;

/**
 *  KMP算法
 * @author 周德永
 * @date 2021/11/4 23:57
 */
public class KMP {
    public static int getIndexOf(String s,String m){
        if (s == null || m == null){
            return -1;
        }
        char[] str1 = s.toCharArray();
        char[] str2 = m.toCharArray();

        int i1 = 0;
        int i2 = 0;
        int[] next = getNextArray(str2);

        while (i1 < str1.length && i2 < str2.length){
            if (str1[i1] == str2[i2]){
                i1++;
                i2++;
            }else if (next[i2] == -1){
                i1++;
            }else {
                i2 = next[i2];
            }
        }
        return i2 == str2.length ? i1 - i2 : -1;
    }

    private static int[] getNextArray(char[] ms) {
        if (ms.length == 1){
            return new int[]{-1};
        }
        int[] next = new int[ms.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2; //next数组的位置
        int cn = 0;
        while (i < next.length){
            if (ms[i-1]==ms[cn]){
                next[i++] = ++cn;
            }
            else if (cn > 0){
                cn = next[cn];
            }else {
                next[i++] = 0;
            }
        }
        return next;
    }

    public static int[] getNext(char[] chs){
        if (chs == null || chs.length == 1){
            return new int[]{-1};
        }
        int[] next = new int[chs.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int cn = 0;
        while (i < chs.length){
            if (chs[i-1] == chs[cn]){
                next[i++] = ++cn;
            }else if (cn > 0){
                cn = next[cn];
            }else {
                next[i++] = 0;
            }
        }
        return next;
    }

    public static int getIndex(String s,String s1){
        char[] str1 = s.toCharArray();
        char[] str2 = s1.toCharArray();
        int i1 = 0;
        int i2 = 0;
        int[] next = getNextArray(str2);
        while (i1 < str1.length && i2 < str2.length){
            if (str1[i1] == str2[i2]){
                i1++;
                i2++;
            }else if (next[i2] != -1){
                i2 = next[i2];
            }else {
                i1++;
            }
        }
        return str2.length == i2 ? i1-i2:-1;
    }

    public static void main(String[] args) {
        String str1 = "asbsdadazhoudeyongdajidlahjghsd";
        String str2 = "zhoudeyong";
        System.out.println(getIndex(str1, str2));
    }
}
