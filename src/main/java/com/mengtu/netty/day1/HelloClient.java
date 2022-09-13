package com.mengtu.netty.day1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        //1.启动类
        Bootstrap bootstrap = new Bootstrap();
        //2.添加EventLoop
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group);
        //3.选择客户端channel实现
        bootstrap.channel(NioSocketChannel.class);
                //4.添加处理器
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                    @Override   //在建立连接后被调用
                    protected void initChannel(SocketChannel ch) throws Exception {
                        log.debug("connected...");
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override//会在建立channel建立成功之后 会触发active事件
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                ByteBuf buffer = ctx.alloc().buffer();
                                for (int i = 0; i < 10; i++) {
                                    buffer.writeBytes(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
                                }
                                ctx.writeAndFlush(buffer);
                            }
                        });
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("localhost", 8900));
        channelFuture.channel().closeFuture().sync();
    }
}
