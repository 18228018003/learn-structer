package cn.cast.dp;

public class Hanoi {
    public static void main(String[] args) {
        hanoi("A","B","C",3);
    }

    public static void hanoi(String source,String help,String target,int n){
        if (n == 1) {
            System.out.println(source+" --> "+target);
            return;
        }
        hanoi(source,target,help,n-1);
        System.out.println(source + " --> " + target);
        hanoi(help,source,target,n-1);
    }

}
