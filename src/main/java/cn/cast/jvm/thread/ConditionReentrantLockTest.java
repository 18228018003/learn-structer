package cn.cast.jvm.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionReentrantLockTest {
    static ReentrantLock lock = new ReentrantLock();
    static Condition waitCigaretteQueue = lock.newCondition();
    static Condition waitBreakfastQueue = lock.newCondition();
    static volatile boolean hasCigarette = false;
    static volatile boolean hasBreakfast = false;
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                lock.lock();
                while (!hasCigarette) {
                    try {
                        waitCigaretteQueue.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName()+" :等到了它的烟");
            } finally {
                lock.unlock();
            }
        },"t1").start();
        new Thread(() -> {
            try {
                lock.lock();
                while (!hasBreakfast) {
                    try {
                        waitBreakfastQueue.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName()+" :等到了它的早餐");
            } finally {
                lock.unlock();
            }
        }).start();
        Thread.sleep(1000);
        sendBreakfast();
        Thread.sleep(1000);
        sendCigarette();
    }
    private static void sendCigarette() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+" :送烟来了");
            hasCigarette = true;
            waitCigaretteQueue.signal();
        } finally {
            lock.unlock();
        }
    }
    private static void sendBreakfast() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+" :送早餐来了");
            hasBreakfast = true;
            waitBreakfastQueue.signal();
        } finally {
            lock.unlock();
        }
    }
}
