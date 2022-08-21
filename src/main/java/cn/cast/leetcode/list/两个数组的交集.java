package cn.cast.leetcode.list;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 给你两个整数数组 nums1 和 nums2 ，请你以数组形式返回两数组的交集。
 * 返回结果中每个元素出现的次数，应与元素在两个数组中都出现的次数一致（如果出现次数不一致，则考虑取较小值）。
 * 可以不考虑输出结果的顺序。
 *
 * @author 周德永
 * @date 2021/12/19 22:47
 */
public class 两个数组的交集 {


    public static void main(String[] args) {
        int[] nums1 = {1,2,2,1}, nums2 = {2,2};
        int[] nums3 = {4,9,5}, nums4 = {9,4,9,8,4};
        System.out.println(Arrays.toString(intersect(nums3, nums4)));
    }

    public static int[] intersect(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i=0,j=0;
        ArrayList<Integer> list = new ArrayList<>();
        while (i < nums1.length && j < nums2.length){
            if (nums1[i] < nums2[j]){
                // 如果i指向的值小于j指向的值,i往后移一步
                i++;
            }else if (nums1[i] > nums2[j]){
                // 如果j指向的值小于i指向的值,j往后移一步
                j++;
            }else {
                // 如果i和j指向的值相同，说明这两个值是重复的，
                list.add(nums1[i]);
                i++;
                j++;
            }
        }
        //把list转化为数组
        int[] res = new int[list.size()];
        for (int k = 0; k < list.size(); k++) {
            res[k] = list.get(k);
        }
        return res;
    }
}
