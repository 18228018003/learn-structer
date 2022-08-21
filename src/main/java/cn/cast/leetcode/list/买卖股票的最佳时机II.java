package cn.cast.leetcode.list;

/**
 * description
 *
 * @author 周德永
 * @date 2021/12/19 22:14
 */
public class 买卖股票的最佳时机II {
    public static void main(String[] args) {
        int[] prices = {7,1,5,3,6,4};
        System.out.println(maxProfit(prices));
    }
    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2)
            return 0;
        int length = prices.length;
        int[][] dp = new int[length][2];
        //dp[i][0] 表示第i+1天未持有股票的最大利益
        //dp[i][1] 表示第i+1天持有股票的最大利益
        dp[0][1] = -prices[0];
        for (int i = 1; i < length; i++) {
            dp[i][1] = Math.max(dp[i-1][1],dp[i-1][0]-prices[i]);
            dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1] + prices[i]);
        }
        //最后一天肯定是手里没有股票的时候，利润才会最大，
        //只需要返回dp[length - 1][0]即可
        return dp[length - 1][0];
    }
    public static int maxProfit2(int[] prices) {
        if (prices == null || prices.length < 2)
            return 0;
        int length = prices.length;
        //初始条件
        int hold = -prices[0];//持有股票
        int noHold = 0;//没持有股票
        for (int i = 1; i < length; i++) {
            //递推公式转化的
            noHold = Math.max(noHold, hold + prices[i]);
            hold = Math.max(hold, noHold - prices[i]);
        }
        //最后一天肯定是手里没有股票的时候利润才会最大，
        //所以这里返回的是noHold
        return noHold;
    }
}
