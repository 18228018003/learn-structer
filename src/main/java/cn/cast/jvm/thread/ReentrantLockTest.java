package cn.cast.jvm.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("启动...");
            try {
                if (!lock.tryLock(2, TimeUnit.SECONDS)) {
                    System.out.println("获取立刻失败，返回");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            try {
                System.out.println("获得了锁");
            } finally {
                lock.unlock();
            }
        }, "t1");
        lock.lock();
        System.out.println("获得了锁");
        t1.start();
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

    public static void test() {
        Thread t1 = new Thread(() -> {
            try {
                /*如果没有竞争则此方法会获得lock锁对象*/
                /*如果有竞争就会进入阻塞队列，可以被其他线程用interrupt方法打断*/
                lock.lockInterruptibly();/*可打断 避免死锁的一种方式*/
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("没有获得锁");
                return;
            }
            try {
                System.out.println("获取到锁");
            } finally {
                lock.unlock();
            }
        }, "t1");
        lock.lock();
        t1.start();
        try {
            Thread.sleep(2000);
            t1.interrupt();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

    /*可重入特性*/
    public static void m() {
        lock.lock();
        try {
            System.out.println("enter main");
            m1();
        }finally {
            lock.unlock();
        }
    }

    private static void m1() {
        lock.lock();
        try {
            System.out.println("enter m1");
            m2();
        }finally {
            lock.unlock();
        }
    }
    private static void m2() {
        lock.lock();
        try {
            System.out.println("enter m2");
        }finally {
            lock.unlock();
        }
    }
}
