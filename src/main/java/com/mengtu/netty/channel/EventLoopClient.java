package com.mengtu.netty.channel;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;


@Slf4j
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        //带有Future。promise的类型都是和异步方法配套使用
        ChannelFuture channelFuture = new Bootstrap()
                .channel(NioSocketChannel.class)
                .group(new NioEventLoopGroup())
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                //1.连接到服务器  异步非阻塞 main发起调用 真正执行connect的是nio线程
                .connect(new InetSocketAddress("localhost", 8900));
        //2.1使用sync()方法同步处理结果
//        channelFuture.sync(); //阻塞住当前线程，直到nio线程连接建立成功
//        Channel channel = channelFuture.channel();
//        log.debug("{}",channel);
        //2.2使用addListener 方法处理异步结果
        channelFuture.addListener(new ChannelFutureListener() {
            @Override //在nio线程连接建立之后会调用operationComplete
            public void operationComplete(ChannelFuture future) throws Exception {
                Channel channel = future.channel();
                log.debug("{}",channel);
//                channel.writeAndFlush("");
            }
        });
    }
}
