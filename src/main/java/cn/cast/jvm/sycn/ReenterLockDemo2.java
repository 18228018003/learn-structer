package cn.cast.jvm.sycn;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone1 implements Runnable{
    Lock lock = new ReentrantLock();
    /**
     * set进去的时候，就加锁，调用set方法的时候，能否访问另外一个加锁的set方法
     */
    public void getLock(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t get Lock");
            setLock();
        }finally {
            lock.unlock();
        }
    }

    private void setLock() {
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t set Lock");
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        getLock();
    }
}
public class ReenterLockDemo2 {
    public static void main(String[] args) {
        Phone1 phone1 = new Phone1();
        /**
         * 因为Phone实现了Runnable接口
         */
        Thread t3 = new Thread(phone1, "t3");
        Thread t4 = new Thread(phone1, "t4");
        t3.start();
        t4.start();
    }
}
