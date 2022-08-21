package cn.cast.jvm.threadpool;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*timer已经废弃了  不建议使用*/
public class TestTimer {
    public static void main(String[] args) {
//        testTimer();
//        scheduleThreadPool();
        scheduleFixRate();
    }

    private static void scheduleFixRate() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
        System.out.println(Thread.currentThread().getName() + " start");
        pool.scheduleAtFixedRate(()->{
            System.out.println(Thread.currentThread().getName() + " 定时执行任务");
        },1,1,TimeUnit.SECONDS);
        pool.scheduleWithFixedDelay(()->{
            System.out.println("running");
        },1,1,TimeUnit.SECONDS);
    }

    private static void testTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" task 1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" task 2");
            }
        };
        timer.schedule(task,1000);
        timer.schedule(task2,1000);
    }

    /*延时执行任务*/
    private static void scheduleThreadPool(){
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
        pool.schedule(()->{
            System.out.println(Thread.currentThread().getName() + " Task 1");
            int i = 1 /0 ;
        },1, TimeUnit.SECONDS);

        pool.schedule(()->{
            System.out.println(Thread.currentThread().getName() + " Task 2");
        },1, TimeUnit.SECONDS);
    }
}
