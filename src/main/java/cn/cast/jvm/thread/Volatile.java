package cn.cast.jvm.thread;

import java.util.concurrent.TimeUnit;

public class Volatile {
    static boolean flag = false;
    public static void main01(String[] args) {
        new Thread(()->{
            while (!flag){
                System.out.println();
            }
        },"t1").start();

        try {
            Thread.sleep(1000);
            flag = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TwoPhaseTermination1 y = new TwoPhaseTermination1();
        y.start();
        y.start();
        y.start();
    }
}
class TwoPhaseTermination1{
    /*监控线程*/
    private Thread monitorThread;
    /*停止标记*/
    private volatile boolean stop = false;
    /*判断是否已经执行过start方法*/
    private boolean starting = false;

    public void start(){
        synchronized (this){
            if (starting)
            {
                return;
            }
            starting = true;
        }
        monitorThread = new Thread(()->{
            while (true){
//                Thread thread = Thread.currentThread();

                if (stop)
                {
                    System.out.println("执行最后操作，保存退出");
                    break;
                }
                try {
                    System.out.println("记录相关信息");
                    TimeUnit.SECONDS.sleep(1); //情况1
                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    thread.interrupt();
                }
            }
        },"监控线程");

        monitorThread.start();
    }

    public void stop(){
        stop = true;
        monitorThread.interrupt();
    }
}