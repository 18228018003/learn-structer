package cn.cast.leetcode.list;

/**
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 *
 * 示例 1:
 * 输入: [2,2,1]
 * 输出: 1
 *
 * 示例 2:
 * 输入: [4,1,2,1,2]
 * 输出: 4
 *
 * @author 周德永
 * @date 2021/12/19 22:36
 */
public class 只出现一次的数字 {
    public static void main(String[] args) {
        int[] nums = {2,2,1};
        int[] nums1 = {4,1,2,1,2};
        System.out.println(singleNumber(nums1));
    }

    public static int singleNumber(int[] nums) {
        if(nums==null || nums.length==0) return -1;
        int eof = nums[0];
        for(int i=1;i < nums.length;i++){
            eof ^= nums[i];
        }
        return eof;
    }
}
