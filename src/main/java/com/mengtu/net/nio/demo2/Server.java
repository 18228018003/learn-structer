package com.mengtu.net.nio.demo2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static com.mengtu.utils.ByteBufferUtil.debugRead;

public class Server {
    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8888));
        ssc.configureBlocking(false);
        List<SocketChannel> channels = new ArrayList<>();
        while (true){
            SocketChannel sc = ssc.accept();
            if (sc != null){
                sc.configureBlocking(false);
                channels.add(sc);
            }
            for (SocketChannel channel : channels) {
                channel.read(buffer);
                channel.configureBlocking(false);
                buffer.flip();
                debugRead(buffer);
                buffer.flip();
            }
        }
    }
}
