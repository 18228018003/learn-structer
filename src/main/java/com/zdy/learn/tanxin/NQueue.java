package com.zdy.learn.tanxin;

import java.util.HashMap;

/**
 * N皇后问题
 *
 * @author 周德永
 * @date 2021/11/1 0:04
 */
public class NQueue {
    public static int num(int n) {
        if (n < 1) {
            return 0;
        }
        int[] record = new int[n]; /*record[i] -> i行的皇后，放在了第几列*/
        return process(0, record, n);

    }

    private static int process(int i, int[] record, int n) {

        if (i == n) {/*终止行*/
            return 1;
        }
        int res = 0;
        for (int j = 0; j < n; j++) {
            /*当前i行的皇后，放在j列，会不会和之前的(0...i-1)的皇后，共行共列或者共斜线*/
            /*如果是，认为无效*/
            /*如果不是，认为有效*/
            if (isValid(record, i, j)) {
                record[i] = j;
                res += process(i + 1, record, n);
            }
        }
        return res;
    }

    private static boolean isValid(int[] record, int i, int j) {
        for (int k = 0; k < i; k++) {/*之前的某个k行的皇后*/
            if (j == record[k] || (Math.abs(record[k] - j) == Math.abs(i - k))) {
                return false;
            }
        }
        return true;
    }

    public static int num2(int n) {
        if (n < 1 || n > 32) {
            return 0;
        }
        int limit = n == 32 ? -1 : (1 << n) - 1;
        return process2(limit,0,0,0);
    }

    public static int process2(int limit, int colLim, int leftDiaLim, int rightDialim) {
        if (colLim == limit) { /*base case*/
            return 1;
        }
        int mostRightOne = 0;

        /*能够选择防止皇后的位置*/
        int pos = limit & (~(colLim | leftDiaLim | rightDialim));
        int res = 0;
        while (pos != 0) {
            mostRightOne = pos & (~pos + 1);
            pos = pos - mostRightOne;
            res += process2(limit, colLim | mostRightOne, (leftDiaLim | mostRightOne) << 1, (rightDialim | mostRightOne) >>> 1);
        }
        return res;
    }

    public static void main(String[] args) {
        long l1 = System.currentTimeMillis();
        System.out.println(num2(16));
        long e1 = System.currentTimeMillis();
        System.out.println((e1 - l1)+" ms");
        HashMap<Object, Object> map = new HashMap<>();

//        long l = System.currentTimeMillis();
//        System.out.println(num(14));
//        long e = System.currentTimeMillis();
//        System.out.println((e - l)+" ms");


    }
}
