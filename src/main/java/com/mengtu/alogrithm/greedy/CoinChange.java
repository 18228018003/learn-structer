package com.mengtu.alogrithm.greedy;

import java.util.Arrays;

public class CoinChange {
    public static void main(String[] args) {
        Integer[] faces = {25,5,10,1};
        Arrays.sort(faces, (f1,f2)->f2-f1);
        coinChange2(faces);
    }

    private static void coinChange2(Integer[] faces) {
        int money = 41, coins = 0, i = 0;
        while (i < faces.length){
            if (money < faces[i]){
                i++;
                continue;
            }
            System.out.println(faces[i]);
            money -= faces[i];
            coins++;
        }
    }

    private static void coinChange(int[] faces) {
        int money = 41,coins = 0;
        for (int i = faces.length - 1; i >= 0 ; i--) {
            if (money < faces[i]) continue;
            System.out.println(faces[i]);
            money -= faces[i];
            coins++;
            i = faces.length;
        }
        System.out.println(coins);
    }

}
