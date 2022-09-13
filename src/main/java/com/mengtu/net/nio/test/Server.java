package com.mengtu.net.nio.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

import static com.mengtu.utils.ByteBufferUtil.debugAll;

public class Server {
    public static void split(ByteBuffer source){
        source.flip();
        int limit = source.limit();
        for (int i = 0; i < limit; i++) {
            if (source.get(i) == '\n'){
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                debugAll(target);
            }
        }
        source.compact();
    }
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8989));
        ssc.configureBlocking(false);
        SelectionKey sscKey = ssc.register(selector, 0, null);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
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
                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);
                }else if (key.isReadable()){
                   try {
                       SocketChannel channel = (SocketChannel) key.channel();
                       ByteBuffer buffer = (ByteBuffer) key.attachment();
                       int read = channel.read(buffer);
                       if (read == -1){
                           key.cancel();
                       }else {
                           split(buffer);
                           if (buffer.position() == buffer.limit()){
                               ByteBuffer byteBuffer = ByteBuffer.allocate(buffer.capacity() << 1);
                               buffer.flip();//切换为读模式 以为split方法将buffer切换为了写模式
                               byteBuffer.put(buffer);
                               key.attach(byteBuffer);
                           }
                       }
                   }catch (IOException e){
                       key.cancel();
                   }
                }
            }
        }
    }
}
