package com.mengtu.netty.stickingwrapping;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 粘包半包问题解决
 * 粘包：接收方一次接收了多余的数据
 * 半包：发送方的数据接受发多次接受
 */
@Slf4j
public class Server {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.group(boss,worker);
        //调整接受缓冲区大小(整个服务器大小)  便于观察半包现象
//        serverBootstrap.option(ChannelOption.SO_RCVBUF,10);
//        serverBootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR,new AdaptiveRecvByteBufAllocator(16,16,16));
        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
//                ch.pipeline().addLast(new FixedLengthFrameDecoder(10));
                ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
            }
        });
        ChannelFuture future = serverBootstrap.bind(8000);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                Channel channel = future.channel();
//                channel.close();
                log.debug("{}",channel);
            }
        });
//        future.channel().closeFuture().addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) throws Exception {
//                boss.shutdownGracefully();
//                worker.shutdownGracefully();
//            }
//        });
    }
}
