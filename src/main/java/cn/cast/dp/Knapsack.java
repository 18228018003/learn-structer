package cn.cast.dp;

/**
 * description
 *
 * @author 周德永
 * @date 2021/12/14 22:56
 */
public class Knapsack {
    public static void main(String[] args) {
        int[] values = {6,3,5,4,6};
        int[] weights = {2,2,6,5,4};
        /*
            i = 5;
            j = 10;
            如果不选择第i件物品 dp(i,j) = dp(i-1,j);
            如果选择第i件物品   dp(i,j) = dp(i-1,j-weights[i])+value[i];
        * */
        int capacity = 10;
        System.out.println(knapa(values,weights,capacity));
        System.out.println(countPrimes(10));
    }

    public static int countPrimes(int n) {
        int ans = 0;
        for (int i = 2; i < n; ++i) {
            ans += isPrime(i) ? 1 : 0;
        }
        return ans;
    }

    public static boolean isPrime(int x) {
        for (int i = 2; i * i <= x; ++i) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int knapa(int[] values,int[] weights,int capacity)
    {
        int[] dp = new int[capacity+1];
        for (int i = 1; i <= values.length; i++) {
            for (int j = capacity; j >= weights[i-1] ; j--) {
                if (j < weights[i-1]) continue;
                dp[j] = Math.max(dp[j],values[i-1]+dp[j-weights[i-1]]);
            }
        }
        return dp[capacity];
    }








    /*优化2*/
    public static int maxValue2(int[] values,int[] weights,int capacity){
        if (values==null||values.length==0) return 0;
        if (weights==null||weights.length==0) return 0;
        if (values.length != weights.length || capacity <= 0) return 0;
        int[] dp = new int[capacity+1];
        for (int i = 1; i <= values.length ; i++) {
            for (int j = capacity; j >= weights[i-1] ; j--) {
                dp[j] = Math.max(dp[j],values[i-1]+dp[j-weights[i-1]]);
            }
        }
        return dp[capacity];
    }

    /*优化1*/
    public static int maxValue1(int[] values,int[] weights,int capacity){
        if (values==null||values.length==0) return 0;
        if (weights==null||weights.length==0) return 0;
        if (values.length != weights.length || capacity <= 0) return 0;
        int[] dp = new int[capacity+1];
        for (int i = 1; i <= values.length ; i++) {
            for (int j = capacity; j >= 1 ; j--) {
                if (j < weights[i-1]) continue;
                dp[j] = Math.max(dp[j],values[i-1]+dp[j-weights[i-1]]);
            }
        }
        return dp[capacity];
    }







    /*dp[i][j]是最大承载为j，有前i件物品，可选时的最大总价值，i 属于 [0,n] j属于[0,w]*/
    /*dp[i][0] = 0 ，最大承载为0； dp[0][j]=0 可选物品为0*/
    public static int maxValue(int[] values,int[] weights,int capacity){
        if (values==null||values.length==0) return 0;
        if (weights==null||weights.length==0) return 0;
        if (values.length != weights.length || capacity <= 0) return 0;

        int[][] dp = new int[values.length+1][capacity+1];

        for (int i = 1; i <= values.length; i++) {
            for (int j = 1; j <= capacity; j++) {
                if (j < weights[i-1]) {
                    dp[i][j] = dp[i-1][j];
                }else {
                    dp[i][j] = Math.max(dp[i-1][j],dp[i-1][j-weights[i-1]]+values[i-1]);
                }
            }
        }
        return dp[values.length][capacity];
    }
}
