package cn.cast.jvm.threadpool;

import java.util.concurrent.*;

public class TestSubmit {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        testNewFixedThreadPool();
//        testNewSingleThreadPool();
        testSynchronousQueue();
    }
    static void testNewFixedThreadPool() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<?> future = pool.submit(() -> "ok");
        Object o = future.get();
        System.out.println(o);
    }
    static void testNewSingleThreadPool(){
        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.execute(()->{
            int i = 1 / 0;
        });
        pool.execute(()->{
            System.out.println(Thread.currentThread().getName()+" 1");
        });
        pool.execute(()->{
            System.out.println(Thread.currentThread().getName()+" 2");
        });
    }
    static void testSynchronousQueue(){
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();
        new Thread(()->{
            try {
                System.out.println("putting 1");
                synchronousQueue.put(1);
                System.out.println("putted 1");

                System.out.println("putting 2");
                synchronousQueue.put(2);
                System.out.println("putted 2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();


        new Thread(()->{
            try {
                System.out.println("taking 1");
                synchronousQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();

        try {
            Thread.sleep(102220);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            try {
                System.out.println("taking 2");
                synchronousQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();
    }
}
