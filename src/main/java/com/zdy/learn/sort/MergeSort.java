package com.zdy.learn.sort;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  归并排序：
 *  利用递归，左边排好序，右边排好序。 归并的过程将左右都排好序
 *  时间复杂度O(N * logN)  额外空间复杂度 O(N)
 * @author 周德永
 * @date 2021/10/24 17:39
 */
public class MergeSort {
    @Test
    public void test2(){
        /*小数和*/
        int[] arr = {2,42,3,4,6,12,331};
        int sum = smallSum(arr);
        System.out.println(sum);
    }

    private int smallSum(int[] arr) {
        if (arr==null || arr.length<2){
            return 0;
        }
        return processSum(arr,0,arr.length-1);
    }

    private int processSum(int[] arr, int l, int r) {
        if (l==r){
            return 0;
        }
        int mid = l + ((r-l)>>1);

        return processSum(arr,l,mid)
                +processSum(arr,mid+1,r)
                +mergeSum(arr,l,mid,r);
    }

    private int mergeSum(int[] arr, int l, int mid, int r) {
        int[] help = new int[r-l+1];
        int i = 0;
        int res = 0;
        int p1 = l;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= r){
            res += arr[p1] < arr[p2] ? (r-p2+1) * arr[p1] : 0;
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= mid){
            help[i++] = arr[p1++];
        }
        while (p2 <= r){
            help[i++] = arr[p2++];
        }
        for (int j = 0; j < help.length; j++) {
            arr[l+j] = help[j];
        }
        return res;
    }

    @Test/*归并排序*/
    public void test()
    {
        int[] arr = {6,5,4,3,2,1};
        mergeSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void mergeSort(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        process(arr, 0, arr.length-1);
    }

    private static void process(int[] arr, int l, int r) {
        if (l==r){
            return;
        }
        int mid = l+((r-l)>>1);
        process(arr,l,mid);
        process(arr,mid+1,r);
        merge(arr,l,mid,r);
    }

    private static void merge(int[] arr, int l, int mid, int r) {
        int[] help = new int[r-l+1];
        int i = 0;
        int p1 = l;
        int p2 = mid+1;
        while (p1 <= mid && p2 <= r){
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++]:arr[p2++];
        }
        while (p1 <= mid){
            help[i++] = arr[p1++];
        }
        while (p2 <= r){
            help[i++] = arr[p2++];
        }
        System.arraycopy(help, 0, arr, l, help.length);
    }

    @Test
    public void test3(){
        List<Integer> list = Arrays.asList(1, 2, 3);
        int[] nums = {-1,0,1,2,-1,-4};
        int i = Integer.parseInt("1010");
        System.out.println(Long.parseLong("11", 2));
        System.out.println(Long.toBinaryString(10));
//        System.out.println(threeSum(nums));
//        sort();
//        System.out.println(Arrays.toString(arr));
    }
    public List<List<Integer>> threeSum1(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        int length = nums.length;
        if(nums == nums || nums.length < 3) return ans;
        Arrays.sort(nums);

        for(int i = 0; i < length; i++){
            if(nums[i] > 0) break;
            if(i > 0 && nums[i] == nums[i-1]) continue;
            int L  = i + 1;
            int R = length - 1;
            while(L < R){
                int sum = nums[i] + nums[L] + nums[R];
                if(sum == 0){
                    ans.add(Arrays.asList(nums[i],nums[L],nums[R]));
                    while(L < R && nums[L] == nums[L+1]) L++;
                    while(L < R && nums[R] == nums[R-1]) R--;
                    L++;
                    R--;
                }
                else if(sum < 0) L++;
                else R--;
            }
        }

        return ans;
    }
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList();
        if(nums == null || nums.length < 3) return ans;
        int len = nums.length;
        Arrays.sort(nums); // 排序
        for (int i = 0; i < len ; i++) {
            if(nums[i] > 0) break; // 如果当前数字大于0，则三数之和一定大于0，所以结束循环
            if(i > 0 && nums[i] == nums[i-1]) continue; // 去重
            int L = i+1;
            int R = len-1;
            while(L < R){
                int sum = nums[i] + nums[L] + nums[R];
                if(sum == 0){
                    ans.add(Arrays.asList(nums[i],nums[L],nums[R]));
                    while (L<R && nums[L] == nums[L+1]) L++; // 去重
                    while (L<R && nums[R] == nums[R-1]) R--; // 去重
                    L++;
                    R--;
                }
                else if (sum < 0) L++;
                else R--;
            }
        }
        return ans;
    }

    int[] arr = new int[]{2,1,3,4,7,6,9};
    int[] help = new int[arr.length >> 1];
    public void sort(){
        if (arr == null || arr.length < 2) return;
        sort1(0,arr.length);
    }


    private void sort1(int left, int right) {
        if ((right - left) < 2) return;
        int mid = (left + right) >> 1;
        sort1(left,mid);
        sort1(mid,right);
        msort(left,mid,right);
    }

    private void msort(int left, int mid, int right) {
        int li = 0, le = mid - left;
        int ri = mid;
        int ai = left;
        for (int i = li; i < le; i++) {
            help[i] = arr[left + i];
        }
        while (li < le){
            if (ri < right && help[li] <= arr[ri]){
                arr[ai++] = help[li++];
            }else {
                arr[ai++] = arr[ri++];
            }
        }
    }
}
