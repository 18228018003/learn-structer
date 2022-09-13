package com.mengtu.net.nio.demo1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static com.mengtu.utils.ByteBufferUtil.debugRead;

//使用NIO理解阻塞模式
public class Server {
    public static void main(String[] args) throws IOException {
        //0.ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        //1.创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //2.绑定监听端口
        ssc.bind(new InetSocketAddress(8888));
        //3.连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true){
            //4.建立与客户端的连接 socketChannel与客户端通信
            System.out.println("connecting ...");
            SocketChannel sc = ssc.accept();//阻塞方法，线程停止等待
            System.out.println("connected ...");
            channels.add(sc);
            //5.接受客户端发送的数据
            for (SocketChannel channel : channels) {
                System.out.println("reading");
                channel.read(buffer);//阻塞方法，线程停止等待
                buffer.flip();
                debugRead(buffer);
                buffer.flip();
                System.out.println("after read");
            }
        }
    }
}
