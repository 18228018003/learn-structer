package cn.cast.tanxin;

import org.junit.Test;

import java.util.Arrays;

/**
 *
 * @author 周德永
 * @date 2021/12/11 14:35
 */
public class CoinChange {
    public static void main(String[] args) {
        int[] faces = {25,10,5,1};
        int money = 41,coins = 0;
        Arrays.sort(faces);
        for (int i = faces.length-1; i >= 0 ; i--) {
            if (money < faces[i]) continue;
            System.out.println(faces[i]);
            money -= faces[i];
            coins++;
            i++;
        }
        System.out.println(coins);
    }
    @Test
    public void ted(){
        int[] bills = {5,10,5,10,20};
        System.out.println(lemonadeChange(bills));
    }
    public boolean lemonadeChange(int[] bills) {
        int five = 0,ten = 0;
        for(int x:bills){
            if(x == 5){
                five++;
            }else if(x == 10){
                if(five==0) return false;
                five--;
                ten++;
            }else{
                if (five > 0 && ten > 0){
                    five--;
                    ten--;
                }else if (five >= 3){
                    five -= 3;
                }else {
                    return false;
                }
            }
        }
        return true;
    }
}
