package com.zdy.learn.bio.four;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 目标：开发实现伪异步通信架构
 *
 * @author 周德永
 * @date 2021/11/8 22:36
 */
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(9001);
            /*初始化一个线程池*/
            HandlerSocketServerPool serverPool = new HandlerSocketServerPool(12, 20);
            while (true) {
                Socket client = server.accept();
                ServerRunnableTarget target = new ServerRunnableTarget(client);
                serverPool.execute(target);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static class ServerRunnableTarget implements Runnable {
        private final Socket socket;

        public ServerRunnableTarget(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String msg;
                while ((msg = reader.readLine()) != null) {
                    System.out.println(msg);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static class HandlerSocketServerPool {
        /* 1.创建一个线程池的成员变量用于存储线程池对象*/
        private final ExecutorService executorService;

        /* 2.创建这个类的对象 初始化线程池对象*/
        public HandlerSocketServerPool(int maxThreadNum, int queueSize) {
            executorService = new ThreadPoolExecutor(3, maxThreadNum, 120,
                    TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize));

        }

        /*提供一个方法来提交任务给线程池的任务队列来缓存，等线程池来处理*/
        public void execute(Runnable target) {
            executorService.execute(target);
        }
    }
}
