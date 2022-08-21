package cn.cast.jvm.thread;

import java.util.concurrent.locks.LockSupport;

/*线程顺序执行*/
public class Sequence {
    static final Object lock = new Object();
    static boolean t2Running = false;

    public static void main(String[] args) {
        WaitNotify wn = new WaitNotify(1,5);
        new Thread(()->{
            wn.print("a",1,2);
        },"t1").start();
        new Thread(()->{
            wn.print("b",2,3);
        },"t2").start();
        new Thread(()->{
            wn.print("c",3,1);
        },"t3").start();
    }
    public static void ma(){
        Thread t1 = new Thread(() -> {
            synchronized (lock){
                while (!t2Running){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("1");
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            synchronized (lock){
                System.out.println("2");
                t2Running = true;
                lock.notify();
            }
        }, "t2");
        t1.start();
        t2.start();
    }
}
class WaitNotify{
    /*线程等待标记 1 2 3*/
    private int flag;
    private int loopNumber;

    public WaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }
    public void print(String str,int waitFlag,int nextFlag){
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this){
                while (waitFlag != flag){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}
class ParkUnPark{
    private int loopNumber;

    public ParkUnPark(int loopNumber) {
        this.loopNumber = loopNumber;
    }
    public void print(String str,Thread next){
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.print(str);
            LockSupport.unpark(next);
        }
    }
}