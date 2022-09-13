package com.mengtu.net.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost",8989));
        sc.write(StandardCharsets.UTF_8.encode("0123456789abcdefghijk\n"));
        System.in.read();
    }
}
