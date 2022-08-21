package cn.cast.leetcode.string;

/**
 * description
 *
 * @author 周德永
 * @date 2021/12/23 22:18
 */
public class 整数反转 {
    public static void main(String[] args) {
        int num = 987654321;
        System.out.println(reverse(num));
    }
    public static int reverse(int x) {
        int res = 0;
        while (x != 0){
            int t = x % 10;
            int newRes = res*10+t;
            //如果数字溢出，直接返回0
            if ((newRes - t)/10 != res){
                return 0;
            }
            res = newRes;
            x = x/10;
        }
        return res;
    }

    public static int reverse2(int x) {
        long res = 0;
        while (x != 0) {
            res = res * 10 + x % 10;
            x /= 10;
        }
        return (int) res == res ? (int) res : 0;
    }
}
