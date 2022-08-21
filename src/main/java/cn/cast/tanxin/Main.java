package cn.cast.tanxin;

/**
 * description
 *
 * @author 周德永
 * @date 2021/12/11 18:20
 */
public class Main {
    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(maxSubArrayDevice(nums));
    }
    /*分治算法*/
    private static int maxSubArrayDevice(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        return maxSubArray(nums,0,nums.length);
    }

    private static int maxSubArray(int[] nums, int begin, int end) {
        if (end - begin < 2) return nums[begin];
        int mid = (begin+end) >> 1;
        int max = 0;
        int leftMax = Integer.MIN_VALUE;
        int leftSum = 0;
        for (int i = mid - 1; i >= begin; i--) {
            leftSum += nums[i];
            leftMax = Math.max(leftMax,leftSum);
        }

        int rightMax = Integer.MIN_VALUE;
        int rightSum = 0;
        for (int i = mid; i < end; i++) {
            rightSum += nums[i];
            rightMax = Math.max(rightMax,rightSum);
        }
        max = leftMax + rightMax;

        return Math.max(max,Math.max(maxSubArray(nums,begin,mid),maxSubArray(nums,mid,end)));
    }






    private static int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0){
            return 0;
        }
        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            for (int end = begin; end < nums.length; end++) {
                int sum = 0;
                for (int i = begin; i <= end; i++) {
                    sum += nums[i];
                }
                max = Math.max(max,sum);
            }
        }
        return max;
    }

    private static int maxSubArray2(int[] nums) {
        if (nums == null || nums.length == 0){
            return 0;
        }
        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            int sum = 0;
            for (int end = begin; end < nums.length; end++) {
                sum += nums[end];
                max = Math.max(max,sum);
            }
        }
        return max;
    }
}
