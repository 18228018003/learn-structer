package cn.cast.jvm.thread;

public class DeadLock {
    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (a){
                System.out.println("lock A");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b){
                    System.out.println("lock B");
                    System.out.println("操作！");
                }
            }
        },"t1");
        Thread t2 = new Thread(() -> {
            synchronized (b){
                System.out.println("lock B");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a){
                    System.out.println("lock A");
                    System.out.println("操作！");
                }
            }
        },"t2");

        t1.start();
        t2.start();
    }
}
