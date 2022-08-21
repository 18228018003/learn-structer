package cn.cast.recur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*打印全排列*/
public class PrintAllPermutation {
    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6,7};
        List<Integer> list = Arrays.stream(nums).boxed().collect(Collectors.toList());
        System.out.println(list);
//        System.out.println(permute("abc"));
    }
    public static List<String> permute(String str){

        List<String> res = new ArrayList<>();
        if (str == null || str.length() == 0) return res;
        char[] chs = str.toCharArray();
        process(chs,0,res);
        return res;
    }

    //str[0...i-1]已经做好决定
    //str[i...]都有机会来到i位置
    //i终止位置,str当前的样子，就是一种结果加入 res
    private static void process(char[] chs, int i, List<String> res) {
        if (i == chs.length) {
            res.add(String.valueOf(chs));
            return;
        }
        //如果i没有终止,i... 都可以来到i位置
        for (int j = i; j < chs.length; j++) { //j i后面的所有字符都有机会来到i位置
            swap(i,j,chs);
            process(chs,i+1,res);
            swap(i,j,chs);
        }
    }

    private static void swap(int i, int j, char[] chs) {
        char temp = chs[i];
        chs[i] = chs[j];
        chs[j] = temp;
    }
}
