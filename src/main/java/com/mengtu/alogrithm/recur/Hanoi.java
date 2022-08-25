package com.mengtu.alogrithm.recur;

//汉诺塔游戏
public class Hanoi {
    public void hanoi(String from,String to,String help,int n){
        if (n == 1){ //如果只有一个 直接移动
            System.out.println(from + " ---> " + to);
            return;
        }
        //把 n - 1个移动到中转位置上
        hanoi(from,help,to,n-1);
        System.out.println(from + " ---> " + to);
        hanoi(help,to,from,n-1);
    }

    public static void main(String[] args) {
        Hanoi hanoi = new Hanoi();
        hanoi.hanoi("A","C","T",3);
    }
}
