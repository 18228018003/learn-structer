package cn.cast.jvm.thread;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class JoinDemo {
    static int r = 0;
    static int r1 = 0;
    static int r2 = 0;

    public static void main1(String[] args) {
        test2();
    }

    private static void test2() {
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r1 = 10;
        }, "t1");
        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r2 = 20;
        }, "t2");
        Thread t3 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r = 30;
        }, "t2");
        t1.start();
        t2.start();
        t3.start();
        try {
            System.out.println(Thread.currentThread().getName()+" join begin");
            t1.join();
            System.out.println(Thread.currentThread().getName()+" join end");
            t2.join();
            System.out.println(Thread.currentThread().getName()+" join end");
            t3.join();
            System.out.println(Thread.currentThread().getName()+" join end");

            System.out.println("r = "+r + "; r1 = " + r1 + "; r2 = " + r2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*用interrupt打断阻塞的线程*/
    @Test
    public void interrupt(){
        Thread t1 = new Thread(() -> {
            try {
                System.out.println("sleep...");
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }, "t1");
        t1.start();
        try {
            Thread.sleep(100);
            t1.interrupt();
            Thread.sleep(500);
            /*打断标记  sleep wait join 等方法被打断之后 打断标记会被清除*/
            /*初始打断标记为true*/
            System.out.println("打断标记"+t1.isInterrupted());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInterruptNormalThread() throws InterruptedException {
        Thread t1 = new Thread(() -> {
//            try {
//                Thread.sleep(5000);
                while (true){
                    boolean interrupted = Thread.currentThread().isInterrupted();
                    if (interrupted){
                        System.out.println("被打断");
                        break;
                    }
                }
//            } catch (InterruptedException e) {
//                System.out.println(e.getMessage());
//            }
        }, "t1");
        t1.start();
        t1.interrupt();
        System.out.println(t1.isInterrupted());
    }

    public static void mai2n1(String[] args) {
        interruptPark();
    }
    public static void interruptPark(){
        Thread t1 = new Thread(()->{
            System.out.println("park...");
            LockSupport.park();
            System.out.println("unPark");
            System.out.println("打断状态: "+ Thread.currentThread().isInterrupted());
//            System.out.println("打断状态: "+ Thread.interrupted());
            LockSupport.park();
            System.out.println("unPark");

        },"t1");
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
    }

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while (true) {
                boolean interrupted = Thread.currentThread().isInterrupted();
                if (interrupted) {
                    break;
                }
            }
            System.out.println(Thread.currentThread().getName() + "：结束");
        }, "t1");
        t1.setDaemon(true);
        t1.start();
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"：结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
class TwoPhaseTermination{
    private Thread monitor;

    /*启动监控线程*/
    public void start(){
        monitor = new Thread(()->{
            while (true){
                Thread thread = Thread.currentThread();
                if (thread.isInterrupted()){
                    System.out.println("被打断了。。收尾工作");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1); //情况1
                    System.out.println("执行监控记录");//情况2
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    thread.interrupt();//重新设置打断标记
                }
            }
        });
        monitor.start();
    }
    /*终止监控线程*/
    public void stop(){
        monitor.interrupt();
    }

    public static void main(String[] args) {
        TwoPhaseTermination tpt = new TwoPhaseTermination();
        tpt.start();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tpt.stop();
    }
}