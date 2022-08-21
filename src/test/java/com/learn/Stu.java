package com.learn;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * description
 *
 * @author 周德永
 * @date 2022/2/22 22:48
 */
public class Stu {
    public String name;
    public int age;

    public String toHex(int num) {
        if(num == 0) return "0";
        StringBuilder sb = new StringBuilder();
        while(num != 0){
            int u = num & 15;
            char c = (char) (u + '0');
            if (u >= 10) c = (char) (u - 10 + 'a');
            sb.append(c);
            num >>= 4;
        }
        return sb.reverse().toString();
    }
    @Test
    public void main() {
        System.out.println(toHex(160));
    }

    public int removeElement(int[] nums, int val) {
        int j = nums.length - 1;
        for (int i = 0; i <= j; i++) {
            if (nums[i] == val) {
                swap(nums, i--, j--);
            }
        }
        return j + 1;
    }
    void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    public int[] twoSum(int[] arr,int target){
        int i = 0;
        int j = arr.length - 1;
        while (i < j){
            int sum = arr[i] + arr[j];
            if (sum > target){
                j--;
            }else if (sum < target){
                i++;
            }else {
                return new int[]{i+1,j+1};
            }
        }
        return new int[]{-1,-1};
    }
    public String multiply(String num1, String num2) {
        /*其中有一个数为0 则直接返回0*/
        if (num1.equals("0") || num2.equals("0")) return "0";

        //得到数组1 和数组2 的长度，他们相乘不会超过 m+n 位
        int m = num1.length(),n = num2.length();
        int[] res = new int[m+n];

        for (int i = m - 1; i >= 0 ; i--) {
            int n1 = num1.charAt(i) - '0';
            for (int j = n - 1; j >= 0 ; j--) {
                int n2 = num2.charAt(j) - '0';
                //根据乘法竖式的规律，按位进行相乘。
                int sum = (res[i+j+1] + n1 * n2);
                //两数相乘后的结果中这一位上需要更新的值
                res[i+j+1] = sum % 10;
                res[i + j] += sum / 10;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < res.length; i++) {
            if (i==0 && res[i] == 0) continue;
            sb.append(res[i]);
        }
        return sb.toString();
    }
    public String addBinary(String a, String b) {
        int carry = 0;
        StringBuilder ans = new StringBuilder();

        for (int i = a.length()-1, j = b.length()-1; i >= 0 || j >= 0; i--,j--){
            int sum = carry;
            sum += i >= 0 ? a.charAt(i) - '0' : 0;
            sum += j >= 0 ? b.charAt(j) - '0' : 0;
            ans.append(sum % 2);
            carry = sum / 2;

        }
        ans.append(carry == 1 ? '1' : 0);
        return ans.reverse().toString();
    }
    public static int[] getNum(int[] arr){
        int eor = 0;
        for (int i : arr) {
            eor ^= i;
        }
        int rightOne = eor & (~eor + 1);
        int res = 0;
        for (int x : arr) {
            if ( (x & rightOne) != 0){
                res ^= x;
            }
        }
        int two = eor ^ res;
        return new int[]{res,two};
    }
    public static int sub(int a, int b){
        b = add(~b, 1);
        return add(a,b);
    }
    public static int add(int a,int b){
        int sum = a ^ b;
        int carry = a & b;
        while (carry != 0){
           carry = carry << 1;
           int temp = sum;
           sum = sum ^ carry;
           carry = temp & carry;
        }
        return sum;
    }
    public static int multiply(int a,int b){
        int x = a < 0 ? add(~a,1):a;
        int y = b < 0 ? add(~b,1):b;
        int min = Math.min(x, y);
        int max = Math.max(x, y);
        int res = 0;
        int count = 0;
        while (count < min){
            res = add(max,res);
            count = add(count,1);
        }
        if ((a^b)<0){
            res = add(~res, 1);
        }
        return res;
    }
    public static boolean isAnagram(String s,String t)
    {
        Arrays.asList(1,2,3,4,6,9,13).stream()
                .filter(i->i%2==0 ||i%3==0)
                .map(i-> i*i)
                .forEach(System.out::println);
        return false;
    }
    @Override
    public String toString() {
        return "Stu{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public List<List<Integer>> getThreeNumSum(int[] arr){
        List<List<Integer>> res = new LinkedList<>();
        if (arr == null || arr.length < 3) return res;
        int len = arr.length;
        Arrays.sort(arr);
        for (int i = 0; i < len; i++) {
            if (i == 0 && arr[i] > 0) break;
            if (i > 0 && arr[i] == arr[i-1]) continue;
            int l = i + 1;
            int r = len - 1;
            while (l < r){
                int sum = arr[l] + arr[r] + arr[i];
                if (sum == 0){
                    res.add(Arrays.asList(arr[i],arr[l],arr[r]));
                    while (l < r && arr[l] == arr[l+1]) l++;
                    while (l < r && arr[r] == arr[r-1]) l++;
                    l++;
                    r--;
                }else if (sum > 0){
                    r--;
                }else {
                    l++;
                }
            }
        }
        return res;
    }
    public String multiply1(String a,String b){
        if (a.equals("0") || b.equals("0")) return "0";
        int m = a.length(),n = b.length();
        int[] help = new int[m+n];
        for (int i = m-1; i >= 0; i--) {
            int n1 = a.charAt(i) - '0';
            for (int j = n-1; j >= 0 ; j--) {
                int n2 = b.charAt(j) - '0';
                int sum = help[i+j+1] + n1*n2;
                help[i+j+1] = sum % 10;
                help[i+j] += sum / 10;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < help.length; i++) {
            if (i==0 && help[i] == 0) continue;
            sb.append(help[i]);
        }
        return sb.toString();
    }
    @Test
    public void test23()
    {
        int[] arr = {-1,0,1,2,-1,-4};
        String a = "123";
        String b = "456";
        System.out.println(multiply1(a,b));
    }
}
