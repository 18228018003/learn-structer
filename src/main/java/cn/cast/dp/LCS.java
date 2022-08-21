package cn.cast.dp;

/**
 *  给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。
 *
 * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
 *
 * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
 * 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
 *
 * 来源：力扣（LeetCode 1143）
 * @author 周德永
 * @date 2021/12/12 18:09
 */
public class LCS {
    public static void main(String[] args) {
        int[] num1 = {1,2,3,4,9,10,12}, num2 = {1,2,3,4,5,6,7,8,9,10,12};
        System.out.println(lcs01(num1,num2));
    }

    public static int lcs01(int[] nums1,int[] nums2){
        if (nums1==null || nums1.length==0)return 0;
        if (nums2==null || nums2.length==0)return 0;
        int[] rowsNum = nums1, colsNum = nums2;
        if (nums1.length < nums2.length){
            colsNum = nums1;
            rowsNum = nums2;
        }
        int[] dp = new int[colsNum.length+1];
        for (int i = 1; i <= rowsNum.length; i++) {
            int cur = 0;
            for (int j = 1; j <= colsNum.length ; j++) {
                int leftTop = cur;
                cur = dp[j];
                if (colsNum[j-1] == rowsNum[i-1]){
                    dp[j] = leftTop+1;
                }else {
                    dp[j] = Math.max(dp[j-1],dp[j]);
                }
            }
        }
        return dp[colsNum.length];
    }



    public int longestCommonSubsequence(String text1, String text2) {

        if (text1==null||text2==null)return 0;
        char[] chars1 = text1.toCharArray();
        if (chars1.length==0)return 0;
        char[] chars2 = text2.toCharArray();
        if (chars2.length==0)return 0;

        char[] rowsNum = chars1,colsNum = chars2;
        if (chars1.length < chars2.length){
            colsNum = chars1;
            rowsNum = chars2;
        }

        int[] dp = new int[colsNum.length+1];
        for (int i = 1; i <=  rowsNum.length ; i++) {
            int cur = 0;
            for (int j = 1; j <= colsNum.length; j++){
                int leftTop = cur;
                cur = dp[j];
                if (rowsNum[i-1] == colsNum[j-1]){
                    dp[j] = leftTop + 1;
                }else {
                    dp[j] = Math.max(dp[j],dp[j-1]);
                }
            }
        }
        return dp[colsNum.length];
    }
    static int lcs5(int[] nums1, int[] nums2){
        if (nums1==null||nums1.length==0)return 0;
        if (nums2==null||nums2.length==0)return 0;
        int[] rowsNum = nums1,colsNum = nums2;
        if (nums1.length < nums2.length){
            colsNum = nums1;
            rowsNum = nums2;
        }
        int[] dp = new int[colsNum.length+1];

        for (int i = 1; i <=  rowsNum.length ; i++) {
            int cur = 0;
            for (int j = 1; j <= colsNum.length; j++){
                int leftTop = cur;
                cur = dp[j];
                if (rowsNum[i-1] == colsNum[j-1]){
                    dp[j] = leftTop + 1;
                }else {
                    dp[j] = Math.max(dp[j],dp[j-1]);
                }
            }
        }
        return dp[colsNum.length];
    }
    static int lcs4(int[] nums1, int[] nums2){
        if (nums1==null||nums1.length==0)return 0;
        if (nums2==null||nums2.length==0)return 0;
        int[][] dp = new int[2][nums2.length+1];
        for (int i = 1; i <= nums1.length ; i++) {
            int row = i & 1;
            int preRow = (i-1) & 1;
            for (int j = 1; j <= nums2.length; j++){
                if (nums1[i-1] == nums2[j-1]){
                    dp[row][j] = dp[preRow][j-1]+1;
                }else {
                    dp[row][j] = Math.max(dp[preRow][j],dp[row][j-1]);
                }
            }
        }
        return dp[nums1.length & 1][nums2.length];
    }

    static int lcs2(int[] nums1, int[] nums2) {
        if (nums1==null||nums1.length==0)return 0;
        if (nums2==null||nums2.length==0)return 0;
        int[][] dp = new int[nums1.length+1][nums2.length+1];
        for (int i = 1; i <= nums1.length; i++) {
            for (int j = 1; j <= nums2.length; j++) {
                if (nums1[i-1] == nums2[j-1]) {
                    dp[i][j] = dp[i-1][j-1]+1;
                }else {
                    dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return dp[nums1.length][nums2.length];
    }


    /*暴力递归*/
    static int lcs3(int[] nums1,int[] nums2){
        if (nums1==null||nums1.length==0)return 0;
        if (nums2==null||nums2.length==0)return 0;
        return lcs(nums1,nums1.length,nums2,nums2.length);
    }

    private static int lcs(int[] nums1, int i, int[] nums2, int j) {
        if (i == 0 || j==0) return 0;
        if (nums1[i-1] == nums2[j-1]) {
            return lcs(nums1,i-1,nums2,j-1)+1;
        }
        return Math.max(lcs(nums1,i-1,nums2,j),lcs(nums1,i,nums2,j-1));
    }
}
