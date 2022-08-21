package cn.cast.jvm.thread;


import java.util.concurrent.locks.LockSupport;

public class ParkUnParkDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("1");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockSupport.park();
            System.out.println("2");
        }, "t1");
        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LockSupport.unpark(t1);
        System.out.println("结束");
    }
}
