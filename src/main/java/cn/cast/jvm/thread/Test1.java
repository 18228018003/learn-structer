package cn.cast.jvm.thread;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Test1 {
    public void main32() throws ExecutionException, InterruptedException {
//        FutureTask<Integer> task = new FutureTask<>(() -> {
//            System.out.println("running");
//            Thread.sleep(1000);
//            return 100;
//        });
//        Thread t = new Thread(task);
//        t.start();
//        System.out.println(task.get());
//        new Thread(()->{
//            while (true){
//                try {
//                    Thread.sleep(300);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(Thread.currentThread().getName()+" running");
//            }
//        },"T1").start();
//        new Thread(()->{
//            while (true){
//                try {
//                    Thread.sleep(300);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(Thread.currentThread().getName()+" running");
//            }
//        },"T2").start();
    }

    @Test
    public void te(){
        Thread t = new Thread(()->{
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ds");
        });
        t.start();
    }

    @Test
    public void tes(){
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        t1.start();
        System.out.println(t1.getState());

        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t1.getState());
    }

    @Test
    public void interrupt(){

        Thread t1 = new Thread(() -> {
            System.out.println("sleep");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("wake up ... ");
            }
        }, "t1");
        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("interrupt");
        t1.interrupt();

    }
    public static void main(String[] args){
        Runnable task1 = ()->{
            int count = 0;
            while (true){
                Thread.yield();
                System.out.println("----->1" + count++);
            }
        };
        Runnable task2 = ()->{
            int count = 0;
            while (true){
                System.out.println("---------------->2" + count++);
            }
        };
        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);
        t1.start();
        t2.start();
    }
    static int r = 0;
    @Test
    public void testJoin() throws InterruptedException {
        test();
    }

    private void test() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+"开始");
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"开始");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"结束");
            r = 10;
        }, "t1");
        t1.start();
        t1.join();
        System.out.println(Thread.currentThread().getName()+"结果为:"+r);
        System.out.println(Thread.currentThread().getName()+"结束");
    }

}
