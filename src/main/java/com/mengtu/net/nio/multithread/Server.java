package com.mengtu.net.nio.multithread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static com.mengtu.utils.ByteBufferUtil.debugAll;

//多线程搭配selector处理事件
public class Server {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        Selector selector = Selector.open();
        ssc.configureBlocking(false);
        SelectionKey sscKey = ssc.register(selector, 0, null);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8900));
        //1.创建固定数量的worker 并初始化
        Worker[] worker = new Worker[2];
        for (int i = 0; i < worker.length; i++) {
            worker[i] = new Worker("worker-"+i);
        }
        AtomicInteger index = new AtomicInteger(0);
        while (true){
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()){
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    System.out.println("before register..."+sc.getRemoteAddress());
                    worker[index.getAndIncrement() % worker.length].register(sc);
                    System.out.println("after register..."+sc.getRemoteAddress());
                }
            }
        }

    }

   static class Worker implements Runnable{
        private Thread thread;
        private Selector selector;
        private String name;
        private volatile boolean start = false;//还未初始化
        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();

        public Worker(String name) {
            this.name = name;
        }

        /**
         * 初始化线程和selector
         */
        public void register(SocketChannel sc) throws IOException {
            if (!start){
                thread = new Thread(this,name);
                selector = Selector.open();
                thread.start();
                start = true;
            }
            //向队列添加了任务 ，但任务并没有执行
            queue.add(()->{
                try {
                    sc.register(selector,SelectionKey.OP_READ,null);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            selector.wakeup();
        }

        @Override
        public void run() {
            while (true){
                try {
                    selector.select();
                    Runnable task = queue.poll();
                    if (task != null){
                        task.run();
                    }
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()){
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel channel = (SocketChannel) key.channel();
                            System.out.println("read..."+channel.getRemoteAddress());
                            channel.read(buffer);
                            buffer.flip();
                            debugAll(buffer);

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
