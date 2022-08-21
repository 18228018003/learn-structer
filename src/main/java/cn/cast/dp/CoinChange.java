package cn.cast.dp;

/**
 * description
 *
 * @author 周德永
 * @date 2021/12/11 22:24
 */
public class CoinChange {
    public static void main(String[] args) {
//        int n = 41;
        int n = 19;
        System.out.println(coins3(n));
    }

    static int coins4(int[] faces,int n){
        if (n < 1 || faces==null ||faces.length < 1) return -1;
        int[] dp = new int[n+1];
        for (int i = 1; i <= n; i++) {
            int min = Integer.MAX_VALUE;
            for (int face : faces) {
                if (i < face||dp[i-face] < 0) break;
                min = Math.min(min,dp[i-face]);
            }
            if (min == Integer.MAX_VALUE){
                dp[i] = -1;
            }else {
                dp[i] = min+1;
            }
        }
        return dp[n];
    }
    /*迭代 自底向上*/
    static int coins3(int n){
        if (n < 1) return -1;
        int[] dp = new int[n+1];
        for (int i = 1; i <= n; i++) {
            int min = dp[i-1];
//            if (i >= 1) min = Math.min(dp[i-1],min);
            if (i >= 5) min = Math.min(dp[i-5],min);
            if (i >= 20) min = Math.min(dp[i-20],min);
            if (i >= 25) min = Math.min(dp[i-25],min);
            dp[i] = min + 1;
        }
        return dp[n];
    }


    /*记忆搜索*/
    static int coins2(int n){
        if (n < 1) return -1;
        int[] dp = new int[n+1];
        int[] faces = {1,5,20,25};
        for (int face : faces) {
            if (n < face) break;
            dp[face] = 1;
        }

        return coins2(n,dp);
    }

    /*记忆化搜索*/
    private static int coins2(int n, int[] dp) {
        if (n < 1) return Integer.MAX_VALUE;
        if (dp[n] == 0){
            int min1 = Math.min(coins(n-25),coins(n-20));
            int min2 = Math.min(coins(n-5),coins(n-1));
            dp[n] = Math.min(min1,min2)+1;
        }
        return dp[n];
    }

    /*暴力递归*/
    static int coins(int n){
        if (n < 1) return Integer.MAX_VALUE;
        if (n==25||n==20||n==5||n==1) return 1;
        int min1 = Math.min(coins(n-25),coins(n-20));
        int min2 = Math.min(coins(n-5),coins(n-1));
        return Math.min(min1,min2)+1;
    }
}
