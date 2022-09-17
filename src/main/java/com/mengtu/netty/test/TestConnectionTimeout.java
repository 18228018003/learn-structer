package com.mengtu.netty.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

public class TestConnectionTimeout {
    public static void main(String[] args) throws InterruptedException {

//        ServerBootstrap bootstrap = new ServerBootstrap();
//        bootstrap.option() 给服务器端配置参数
//        bootstrap.childOption() 给SocketChannel配置参数

        Bootstrap b = new Bootstrap();
        //如果服务器没开 但是又设置了超时时间  明确连不上会报连接异常
        b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,300);
        b.channel(NioSocketChannel.class);
        b.group(new NioEventLoopGroup());
        b.handler(new LoggingHandler());
        ChannelFuture channelFuture = b.connect(new InetSocketAddress("127.0.0.1", 9000));
        channelFuture.sync().channel().closeFuture().sync();
    }
}
