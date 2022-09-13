package com.mengtu.netty.channel;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

@Slf4j
public class TestNettyPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1.准备eventLoop对象
        NioEventLoopGroup group = new NioEventLoopGroup();
        //2.可以主动创建promise  结果容器
        DefaultPromise<Integer> promise = new DefaultPromise<>(group.next());

        new Thread(()->{
            //3 .任意线程执行计算 计算完毕向promise填充结果
            try {
                Thread.sleep(1000);
                log.debug("开始计算...");
                int i = 1 / 0;
                promise.setSuccess(60);
            } catch (InterruptedException e) {
                promise.setFailure(e);
            }
        },"计算线程").start();
//        log.debug("等待结果");
//        log.debug("结果是：{}",promise.get());
        new Thread(()->{
            //4.接受结果线程
            try {
                log.debug("结果是：{}",promise.get());
            } catch (InterruptedException | ExecutionException e) {

            }
        },"等待结果线程").start();

    }
}
