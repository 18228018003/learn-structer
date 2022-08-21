package cn.cast.leetcode.list;

/**
 * 给你一个数组，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
 *
 * @author 周德永
 * @date 2021/12/19 22:28
 */
public class 旋转数组 {
    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6,7};
        int k = 3;
        rotate(nums,3);
    }
    /*
    * 可以使用一个临时数组，先把原数组的值存放到一个临时数组中，
    * 然后再把临时数组的值重新赋给原数组，重新赋值的时候要保证每个元素都要往后移k位，
    * 如果超过数组的长度就从头开始，所以这里可以使用(i + k) % length来计算重新赋值的元素下标
    */
    public static void rotate(int[] nums, int k) {
        int length = nums.length;
        int[] temp = new int[length];
        for (int i = 0; i < length; i++) {
            //把原数组值放到一个临时数组中，
            temp[i] = nums[i];
        }
        //然后在把临时数组的值重新放到原数组，并且往右移动k位
        for (int i = 0; i < length; i++) {
            nums[(i+k)%length] = temp[i];
        }

    }

    public static void rotate2(int[] nums, int k) {
        int hold = nums[0];
        int index = 0;
        int length = nums.length;
        boolean[] visited = new boolean[length];
        for (int i = 0; i < length; i++) {
            index = (index + k) % length;
            if (visited[index]) {
                //如果访问过，再次访问的话，会出现原地打转的现象，
                //不能再访问当前元素了，我们直接从他的下一个元素开始
                index = (index + 1) % length;
                hold = nums[index];
                i--;
            } else {
                //把当前值保存在下一个位置，保存之前要把下一个位置的
                //值给记录下来
                visited[index] = true;
                int temp = nums[index];
                nums[index] = hold;
                hold = temp;
            }
        }
    }

}
