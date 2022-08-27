package com.mengtu.alogrithm.dp;

public class CoinChange {
    public static void main(String[] args) {
        int[] faces = {1,5,20,25};
        System.out.println(coins3(19,faces));
    }
    //暴力递归
    static int coins(int n){
        if (n <= 0) return Integer.MAX_VALUE;
        if (n == 25 || n == 20 || n == 5 || n == 1) return 1;
        int min1 = Math.min(coins(n - 25),coins(n-20));
        int min2 = Math.min(coins(n-5),coins(n-1));
        return Math.min(min1,min2) + 1;
    }
    //记忆化搜索
    static int coins1(int n){
        if(n < 1) return -1;
        int[] dp = new int[n + 1];
        dp[1] = dp[5] = dp[20] = dp[25] = 1;
        return coins1(n,dp);
    }
    private static int coins1(int n, int[] dp) {
        if (n < 1) return Integer.MAX_VALUE;
        if (dp[n] == 0){
            int min1 = Math.min(coins1(n-25,dp),coins1(n-20,dp));
            int min2 = Math.min(coins1(n-5,dp),coins1(n-1,dp));
            dp[n] = Math.min(min1,min2) + 1;
        }
        return dp[n];
    }
    //自底向上
    static int coins3(int n,int[] faces){
        if (n < 1) return -1;
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int min = dp[i - 1];
            if (i >= 5) min = Math.min(dp[i - 5], min);
            if (i >= 20) min = Math.min(dp[i - 20], min);
            if (i >= 25) min = Math.min(dp[i - 25], min);
            dp[i] = min + 1;
        }
        return dp[n];
    }

    static int coins5(int n,int[] faces){
        if (n < 1 || faces == null || faces.length == 0) return -1;
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int min = Integer.MAX_VALUE;
            for (int face : faces) {
                if (i < face || dp[i - face] < 0) continue;
                min = Math.min(dp[i - face], min);
            }
            if (min == Integer.MAX_VALUE){
                dp[i] = -1;
            }else {
                dp[i] = min + 1;
            }
        }
        return dp[n];
    }
}
