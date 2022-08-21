package cn.cast.jvm.threadpool;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestSchedule {
    /*如何让任务在每周四18:00:00执行任务*/
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        /*获取周四时间*/
        LocalDateTime time = now.withHour(18).withMinute(0).withSecond(0).withNano(0).with(DayOfWeek.THURSDAY);
        if (now.compareTo(time) > 0){
            time = time.plusWeeks(1);
        }
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        long period = 1000*60*60*24*7;
        long initialDelay = Duration.between(now,time).toMillis();
        pool.scheduleAtFixedRate(()->{
            System.out.println("running");
        },initialDelay,period, TimeUnit.MILLISECONDS);
    }
}
