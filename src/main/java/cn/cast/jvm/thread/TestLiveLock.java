package cn.cast.jvm.thread;

/*活锁 出现在两个线程互相改变对方的结束条件，最后谁也无法结束，例如*/
public class TestLiveLock {
    static volatile int count = 10;
    static final Object lock = new Object();
    public static void main(String[] args) {
        new Thread(()->{
            while (count > 0){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count--;
                System.out.println(Thread.currentThread().getName()+": "+count);
            }
        },"t1").start();

        new Thread(()->{
            while (count < 20){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                System.out.println(Thread.currentThread().getName()+": "+count);
            }
        },"t2").start();
    }
}
