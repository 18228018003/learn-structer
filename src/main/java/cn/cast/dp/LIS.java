package cn.cast.dp;

/**
 *  给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
 *
 * 子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。
 * 例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
 *
 * 示例 1：
 * 输入：nums = [10,9,2,5,3,7,101,18]
 * 输出：4
 * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4
 *
 * 示例 2：
 * 输入：nums = [0,1,0,3,2,3]
 * 输出：4
 *
 * 示例 3：
 * 输入：nums = [7,7,7,7,7,7,7]
 * 输出：1
 *
 * 来源：力扣（LeetCode 300）
 * @author 周德永
 * @date 2021/12/12 17:37
 */
public class LIS {
    public static void main(String[] args) {
        int[] nums = {10,9,2,5,3,7,101,9,10,11,12,18};
        int i = liss(nums);
        System.out.println(i);
    }

    public static int liss(int[] nums){
        if (nums==null||nums.length==0)return 0;

        int len = 0;
        int[] top = new int[nums.length];

        for (int num : nums) {
            int i = 0;
            while (i < len){
                if (top[i] >= num ){
                    top[i] = num;
                    break;
                }
                i++;
            }
            if (i==len){
                len++;
                top[i] = num;
            }
        }
        return len;
    }
    public static int lis1(int[] nums){
        if (nums==null||nums.length==0)return 0;

        int[] dp = new int[nums.length];
        int max = dp[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i]<=nums[j]) continue;
                dp[i] = Math.max(dp[j]+1,max);
            }
            max = Math.max(max,dp[i]);
        }
        return max;
    }


    /*使用二分搜索来做*/
    static int lengthOfLis(int[] nums){
        if (nums == null || nums.length==0) return 0;
        int len = 0;/*牌堆的数量 开始为0*/
        int[] top = new int[nums.length];
        //遍历所有的牌 10,9,2,5,3,7,101,9,10,11,12,18
        for (int num : nums) {
            int j = 0;
            while (j < len){
                /*找到一个 >= num的牌顶*/
                if (top[j] >= num) {
                    top[j] = num;
                    break;
                }
                j++;
            }
            if (j==len) {
                len++;
                top[j] = num;
            }
        }
        return len;
    }






    static int lengthOfLIS1(int[] nums) {
        if (nums==null || nums.length <= 0) return 0;
        int[] dp = new int[nums.length];/*dp[i]表示以第i个数 nums[i]结尾 的最长严格递增子序列的长度*/
        int max = dp[0] = 1;/*初始最大值为dp[0] 默认自己就是最长递增子序列，长度为1*/
        for (int i = 1; i < nums.length ; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] <= nums[j]) continue; /* 第i个数 nums[i] 小于等于前面的数  那么他就不是严格递增 直接跳过 */
//                dp[i] = dp[j]+1; /*第i个数 nums[i] 大于前面的数*/
                /* 10,9,2,5,3,7,101,18 */
                dp[i] = Math.max(dp[j]+1,max);
            }
            max = Math.max(dp[i],max);
        }
        return max;
    }




    /*最长严格递增子序列*/
    static int lengthOfLIS(int[] nums) {
        if (nums==null || nums.length <= 0) return 0;
        int[] dp = new int[nums.length];
        int max = dp[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] <= nums[j]) continue;
                dp[i] = Math.max(dp[j] + 1,max);
            }
            max = Math.max(dp[i],max);
        }
        return max;
    }

    static int lis(int[] nums){
        if (nums == null || nums.length==0) return 0;
        int[] dp = new int[nums.length];
        int max = dp[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] <= nums[j]) continue;
                dp[i] = Math.max(dp[j]+1,max);
            }
            max = Math.max(dp[i],max);
        }
        return max;
    }
}
