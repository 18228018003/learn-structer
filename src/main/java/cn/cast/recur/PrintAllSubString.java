package cn.cast.recur;

import java.util.ArrayList;
import java.util.List;

/*打印字符串的全部子序列*/
public class PrintAllSubString {
    public static List<String> subString(String str){
        ArrayList<String> ans = new ArrayList<>();
        if (str == null || str.length() == 0) return ans;
        char[] chs = str.toCharArray();
        String path = "";
        process(chs,0,path,ans);
        return ans;
    }

    private static void process(char[] chs, int i, String path,List<String> ans) {
        if (i == chs.length) {
            ans.add(path);
            return;
        }
        String no = path;
        process(chs,i+1,no,ans);
        String yes = path + chs[i];
        process(chs,i+1,yes,ans);
    }

    public static void main(String[] args) {
        System.out.println(subString("abc"));
    }
}
