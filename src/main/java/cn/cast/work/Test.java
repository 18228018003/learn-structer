package cn.cast.work;

/**
 * description
 *
 * @author 周德永
 * @date 2021/12/17 21:48
 */
public class Test {

    public String test1(){
        return "test1";
    }
    public String test2(){
        return "test2";
    }
    public String test(){
        return "test";
    }

    public static void main(String[] args) {
        double ln2 = Math.log(2);
        double p = 0.000001;
        int n = 1_0000;
        int size = (int) (-(n * Math.log(p)) / (ln2 * ln2));
        int k = (int) (size*ln2 / n);
        System.out.println(size/64);
        System.out.println(k);
    }

}
