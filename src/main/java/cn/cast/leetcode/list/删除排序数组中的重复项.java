package cn.cast.leetcode.list;

/**
 * description
 *
 * @author 周德永
 * @date 2021/12/19 21:49
 */
public class 删除排序数组中的重复项 {
    public static void main(String[] args) {
        int[] nums = {0,0,1,1,1,2,2,3,3,4};
        System.out.println(removeDuplicates2(nums));
        System.out.println(removeDuplicates(nums));
    }
    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length==0) return 0;
        int left = 0;
        for (int right = 1; right < nums.length; right++) {
            //如果左指针和右指针指向的值一样，说明有重复的，
            //这个时候，左指针不动，右指针继续往右移。如果他俩
            //指向的值不一样就把右指针指向的值往前挪
            if (nums[left] != nums[right]) {
                nums[++left] = nums[right];
            }
        }
        return ++left;
    }

    public static int removeDuplicates2(int[] nums) {
        if (nums == null || nums.length==0) return 0;
        int count = 0;//重复的数字个数
        for (int right = 1; right < nums.length; right++) {
            if (nums[right]==nums[right-1]){
                //如果有重复的，count要加1
                count++;
            }else {
                //如果没有重复，后面的就往前挪
                nums[right-count] = nums[right];
            }
        }
        return nums.length-count;
    }
}
