package cn.cast.jvm.threadpool;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                    Thread.sleep(new Random().nextInt(1000));
                    System.out.println(Thread.currentThread().getName()+" 获取许可:" + System.currentTimeMillis());
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName()+" 释放许可:"+ System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"t"+i).start();
        }
    }
}
