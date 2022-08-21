package com.zdy.learn.netty.buffer;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/14 22:48
 */
public class KMP {
    public static int indexOf(String s,String m){
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
        return i2 == str2.length ? i1-i2:-1;
    }

    private static int[] getNextArray(char[] ms) {
        if (ms.length == 1){
            return new int[]{-1};
        }
        int[] next = new int[ms.length];
        next[0] = -1;/*规定next数组第一位为-1 第二位为0*/
        next[1] = 0;
        int i = 2;//next数组的位置
        int cn = 0;

        while (i < next.length){
            if (ms[i-1] == ms[cn]){
                next[i++] = ++cn;
            }else if (cn > 0){
                cn = next[cn];
            }else {
                next[i++] = 0;
            }
        }
        return next;
    }
}
