package com.mengtu.netty.day1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloServer {
    public static void main(String[] args) throws InterruptedException {
        //1.服务器端的启动器，负责组装netty组件
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.option(ChannelOption.SO_RCVBUF,10);
        //3.选择服务器的ServerSocketChannel实现
        serverBootstrap.channel(NioServerSocketChannel.class);
                //2.
        serverBootstrap.group(boss,worker);


                //4.boss负责处理连接 worker负责处理读写 决定了worker能执行那些操作
        serverBootstrap.childHandler(
                        //5.channel代表和客户端进行数据读写的通道 Initializer初始化，负责添加别的handler
                        new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //6.添加具体handler
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    }
                });
                //7.绑定监听端口
        ChannelFuture channelFuture = serverBootstrap.bind(8900);
        log.debug("{} binding...", channelFuture.channel());
        channelFuture.sync();
        log.debug("{} bound...", channelFuture.channel());
        channelFuture.channel().closeFuture().sync();

    }
}
