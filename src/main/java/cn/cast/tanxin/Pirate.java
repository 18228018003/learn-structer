package cn.cast.tanxin;

import java.util.Arrays;

/**
 *
 *海盗船  贪心策略
 * @author 周德永
 * @date 2021/12/11 14:31
 */
public class Pirate {
    public static void mai2n(String[] args) {
        int[] weights = {3,5,4,10,7,14,2,11};
        Arrays.sort(weights);
        int capacity = 30,weight = 0,count = 0;
        for (int i = 0; i < weights.length && weight < capacity; i++) {
            int newWeight = weight+weights[i];
            if (newWeight <= capacity){
                weight = newWeight;
                count++;
                System.out.println(weights[i]);
            }
        }
        System.out.println(count);
    }

    public static void main(String[] args) {
        char ch = 'a';
        char ch1 = 'b';
        System.out.println(ch1 > ch);
    }
}
