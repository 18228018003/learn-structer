package com.mengtu.net.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

public class WriteServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        Selector selector = Selector.open();
        ssc.configureBlocking(false);
        SelectionKey sscKey = ssc.register(selector, 0, null);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8900));
        while (true){
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, 0, null);
                    //1.向客户端发送大量数据
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 500_0000; i++) {
                        sb.append("a");
                    }

                    ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());
                    //2.返回值代表实际写入的数量
                    int write = sc.write(buffer);//返回值代表实际写入的数量
                    System.out.println(write);
                    //3.判断是否有剩余内容
                    if (buffer.hasRemaining()){
                        //4.关注可写事件
                        scKey.interestOps(scKey.interestOps() + SelectionKey.OP_WRITE);
                        //5.把未写完的数据挂到sckey上
                        scKey.attach(buffer);
                    }

                }else if (key.isWritable()){
                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                    SocketChannel sc = (SocketChannel) key.channel();
                    int write = sc.write(byteBuffer);//返回值代表实际写入的数量
                    System.out.println(write);
                    //6.清理操作
                    if (!byteBuffer.hasRemaining()){
                        key.attach(null); //需要清除buffer
                        //不需要再继续关注可写事件
                        key.interestOps(key.interestOps()-SelectionKey.OP_WRITE);
                    }
                }
            }
        }
    }
}
