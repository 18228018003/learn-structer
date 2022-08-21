package cn.cast.recur;

//0-1背包问题
public class Bagpack {
    public static void main(String[] args) {
        int[] weights = {3,2,4,7,1,1};
        int[] values = {5,6,3,19,13,5};
        int bag = 14;
        System.out.println(dpWays(weights,values,bag));
        System.out.println(dpWays2(weights,values,bag));
    }
    public static int process(int[] w, int[] v, int index,int alreadyW, int bag){
        /*重量超了*/
        if (alreadyW > bag) return -1;
        /*重量没超 但是已经没有货了*/
        if (index == w.length) return 0;
        /*没选*/
        int p1 = process(w, v, index+1, alreadyW, bag);
        int p2next = process(w, v, index+1, alreadyW+w[index], bag);
        int p2 = -1;
        if (p2next != -1){
            p2 = v[index] + p2next;
        }
        return Math.max(p1,p2);
    }
    public static int dpWays2(int[] w,int[] v,int bag){
        int n = w.length;
        int[][] dp = new int[n+1][bag+1];
        for (int i = 1; i <= v.length; i++) {
            for (int j = 1; j <= bag; j++) {
                if (j < w[i-1]) {
                    dp[i][j] = dp[i-1][j];
                }else {
                    dp[i][j] = Math.max(dp[i-1][j],dp[i-1][j-w[i-1]] + v[i-1]);
                }
            }
        }
        return dp[n][bag];
    }
    /**
     * @param w 物品重量
     * @param v 物品价值
     * @param index 当前位置
     * @param rest 剩余空间
     * @return 返回index货物能够获得最大价值
     */
    public static int recur(int[] w, int[] v, int index,int rest){
        if (rest <= 0) return 0;
        if (index == w.length) return 0;
        int p1 = recur(w, v, index+1, rest);
        int p2 = Integer.MIN_VALUE;
        if (rest >= w[index]){
            p2 = v[index] + recur(w,v,index+1,rest - w[index]);
        }
        return Math.max(p1,p2);
    }


    public static int dpWays(int[] w,int[] v,int bag){
        int n = w.length;
        int[][] dp = new int[n+1][bag+1];
        for (int i = n-1; i >= 0; i--) {
            for (int j = 1; j <= bag; j++) {
                if (j < w[i]) dp[i][j] = dp[i+1][j];
                else {
                    dp[i][j] = Math.max(dp[i+1][j],dp[i+1][j-w[i]] + v[i]);
                }
            }
        }
        return dp[0][bag];
    }
}
