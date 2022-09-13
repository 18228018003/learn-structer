package com.mengtu.net.bio.four;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HandlerServerSocketPool {
    //1.创建线程池成员变量用于存储线程池对象
    private ExecutorService executorService;

    //2.创建这个类的对象的时候，初始化线程池
    public HandlerServerSocketPool(int maxThread,int queueSize){
        executorService = new ThreadPoolExecutor(3,
                maxThread,
                120,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize));
    }

    //3.提供方法来提交任务给线程池的任务队列来暂存
    public void execute(Runnable target){
        executorService.execute(target);
    }
}
