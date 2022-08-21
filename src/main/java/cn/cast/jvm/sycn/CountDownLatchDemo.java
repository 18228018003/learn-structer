package cn.cast.jvm.sycn;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "\t 上完自习，离开教室");
                latch.countDown();
            },String.valueOf(i)).start();
        }
        latch.await();
        System.out.println(Thread.currentThread().getName() + "\t 班长最后关门");
    }
}
