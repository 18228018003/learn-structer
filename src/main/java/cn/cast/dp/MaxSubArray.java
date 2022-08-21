package cn.cast.dp;

/**
 *  求最大连续子序列的和
 * @author 周德永
 * @date 2021/12/12 17:05
 */
public class MaxSubArray {
    public static void main(String[] args) {
//        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
//        System.out.println(subArray1(nums));
        System.out.println(mulitply("4635","3543"));
        System.out.println(4635*3543);
    }

    public static String mulitply(String a,String b){
        int m = a.length();
        int n = b.length();
        int[] help = new int[m + n];
        for (int i = m-1; i >= 0; i--) {
            int num1 = a.charAt(i) - '0';
            for (int j = n-1; j >= 0; j--) {
                int num2 = b.charAt(j) - '0';
                int res = num1 * num2 + help[i + j + 1];
                help[i+j+1] = res % 10;
                help[i+j] += res / 10;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < help.length; i++) {
            if (i==0 && help[i]==0) continue;
            sb.append(help[i]);
        }
        return sb.toString();
    }

    public static int subArray1(int[] nums){
        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            for (int end = begin; end < nums.length; end++) {
                int sum = 0;
                for (int i = begin; i <= end; i++) {
                    sum += nums[i];
                }
                max = Math.max(sum,max);
            }
        }
        return max;
    }
    public static int subArray2(int[] nums){
        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            int sum = 0;
            for (int end = begin; end < nums.length; end++) {
                sum += nums[end];
                max = Math.max(sum,max);
            }
        }
        return max;
    }
   public static int subArray(int[] nums){
        int dp = nums[0];
        int max = dp;
       for (int i = 1; i < nums.length; i++) {
           if (dp < 0){
               dp = nums[i];
           }else {
               dp = nums[i]+dp;
           }
           max = Math.max(max,dp);
       }
       return max;
   }







    static int maxSubA(int[] nums){
        if (nums == null || nums.length == 0) return -1;
        int dp = nums[0];
        int max = dp;
        for (int i = 1; i < nums.length; i++) {
            if (dp < 0){
                dp = nums[i];
            }else {
                dp =dp+nums[i];
            }
            max = Math.max(max,dp);
        }
        return max;
    }





    static int maxSubArray3(int[] nums)
    {
        int dp = nums[0];/*默认nums[0]为最大值*/
        int max = dp;
        for (int i = 1; i < nums.length; i++) {
            /*-2,1,-3,4,-1,2,1,-5,4*/
            if (dp <= 0){
                dp = nums[i];
            }else {
                dp = nums[i] + dp;
            }
            max = Math.max(max,dp);
        }
        return max;
    }













    static int maxSubArray1(int[] nums){
        if (nums == null||nums.length<1) return 0;
        int dp = nums[0];
        int max = dp;
        for (int i = 1; i < nums.length; i++) {
            if (dp <= 0){
                dp = nums[i];
            }else {
                dp = dp + nums[i];
            }
            max = Math.max(max,dp);
        }
        return max;
    }

    /*空间复杂度为O(n) 时间复杂度为O(n)  继续优化空间复杂度*/
    static int maxSubArray(int[] nums){
        if (nums == null||nums.length<1) return 0;
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0];
        System.out.println("dp[0] = "+dp[0]);
        for (int i = 1; i < nums.length; i++) {
            if (dp[i - 1] <= 0){
                dp[i] = nums[i];
            }else {
                dp[i] = dp[i-1]+nums[i];
            }
            max = Math.max(max,dp[i]);
            System.out.println("dp["+i+"] = " +dp[i]);
        }
        return max;
    }
}
