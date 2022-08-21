package cn.cast.dp;

/**
 * 机器人走路
 */
public class RoBotWalk {
    public static void main(String[] args) {
        System.out.println(ways(5, 2, 6, 4));
        System.out.println(ways3(5, 2, 6, 4));
    }
    public static int ways(int N,int start,int K,int aim){
        return process(start,K,aim,N);
    }

    private static int process(int cur, int rest, int aim, int n) {
        if (rest == 0){
            return cur == aim ? 1 : 0;
        }
        if (cur == 1){
            return process(cur + 1,rest -1, aim,n);
        }
        if (cur == n){
            return process(cur-1,rest-1,aim,n);
        }
        return process(cur-1,rest-1,aim,n) + process(cur+1,rest-1,aim,n);
    }
    public static int ways2(int N,int start,int K,int aim){
        int[][] dp = new int[N+1][K+1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= K; j++) {
                dp[i][j] = -1;
            }
        }
        return process2(start,K,aim,N,dp);
    }

    private static int process2(int cur, int rest, int aim, int n,int[][] dp) {
        if (dp[cur][rest] != -1){
            return dp[cur][rest];
        }
        if (rest == 0){
            dp[cur][rest] = cur == aim ? 1 : 0;
        }else if (cur == 1){
            dp[cur][rest] = process2(cur + 1,rest -1, aim,n,dp);
        }else if (cur == n){
            dp[cur][rest] = process2(cur-1,rest-1,aim,n,dp);
        }else {
            dp[cur][rest] = process2(cur-1,rest-1,aim,n,dp) + process2(cur+1,rest-1,aim,n,dp);
        }
        return dp[cur][rest];
    }
    public static int ways3(int N,int start,int K,int aim){
        int[][] dp = new int[N+1][K+1];
        dp[aim][0] = 1;
        for (int rest = 1; rest <= K; rest++) {
            dp[1][rest] = dp[2][rest-1];
            for (int cur = 2; cur < N; cur++) {
                dp[cur][rest] = dp[cur+1][rest-1] + dp[cur-1][rest-1];
            }
            dp[N][rest] = dp[N-1][rest-1];
        }

        return dp[start][K];
    }
}
