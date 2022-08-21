package cn.cast.jvm.threadpool;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo {
    public static void demo1() {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        String[] arr = new String[10];
        pool.submit(()->{
            for (int j = 0; j <= 100; j++) {
                arr[0] = j+"";
                System.out.println(Arrays.toString(arr));
            }
        });
        pool.shutdown();
    }
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        String[] arr = new String[10];
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            int temp = i;
            pool.submit(()->{
                for (int j = 0; j <= 100; j++) {
                    arr[temp] = j+"";
                    try {
                        Thread.sleep(new Random().nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print("\r"+Arrays.toString(arr));
                }
                latch.countDown();
            });
        }
        latch.await();
        System.out.println("\n游戏开始");
        pool.shutdown();
    }


}
