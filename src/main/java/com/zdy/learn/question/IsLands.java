package com.zdy.learn.question;

/**
 * 岛问题
 * 一个矩阵中只有0和1两种值，
 * 每个位置都可以和自己的上、下、左、右四个位置相连，
 * 如果有一片1连在一起，这个部分叫做一个岛。
 * 求一个矩阵中有多少岛
 * <p>
 * 如：
 * 0 0 1 0 1 0
 * 1 1 1 0 1 0
 * 1 0 0 1 0 0
 * 0 0 0 0 0 0
 * 这个矩阵中有三个岛
 *
 * 进阶 使用并行算法
 * @author 周德永
 * @date 2021/11/2 23:29
 */
public class IsLands {
    public static int countIsLands(int[][] m) {
        if (m == null || m[0] == null) {
            return 0;
        }
        int N = m.length;
        int M = m[0].length;
        int res = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (m[i][j] == 1) {
                    res++;
                    infect(m, i, j, N, M);
                }
            }
        }
        return res;
    }

    private static void infect(int[][] m, int i, int j, int n, int m1) {
        if (i < 0 || i >= n || j < 0 || j >= m1 || m[i][j] != 1) {
            return;
        }
        m[i][j] = 2;
        infect(m,i+1,j,n,m1);
        infect(m,i-1,j,n,m1);
        infect(m,i,j+1,n,m1);
        infect(m,i,j-1,n,m1);
    }

    public static void main(String[] args) {
        int[][] m1 = {
                {0,0,0,0,0,0,0,0,0},
                {0,1,1,1,0,1,1,1,0},
                {0,1,1,1,0,0,0,1,0},
                {0,1,1,0,0,0,0,0,0},
                {0,0,0,0,0,0,1,1,0},
        };
        int i = countIsLands(m1);
        System.out.println(i);

        for (int j = 0; j < 5; j++) {
            for (int k = 0; k < 9; k++) {
                System.out.print(m1[j][k]);
            }
        }
    }
}
