package com.learn.no;

public class Demo8 {
    final static Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;
    public static void main(String[] args) {
        new Thread(()->{
            synchronized (room){
                System.out.println("打到条件没？"+ hasCigarette);
                if (!hasCigarette){
                    System.out.println("没有达到条件 休息会！！");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("打到条件没？"+ hasCigarette);
                if (hasCigarette){
                    System.out.println("达到条件 干活！！");
                }
            }
        },"负责人").start();
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                synchronized (room){
                    System.out.println("干活！！");
                }
            },"其他人").start();
        }
    }

}
