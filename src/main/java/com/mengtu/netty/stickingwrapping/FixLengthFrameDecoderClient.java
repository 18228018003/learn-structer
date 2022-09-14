package com.mengtu.netty.stickingwrapping;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Random;

/**
 * 解决办法2.定长消息解码器 能解决粘包半包问题
 * 缺点：占用字节数较多
 */
@Slf4j
public class FixLengthFrameDecoderClient {
    public static void main(String[] args) {
        send();
    }

    public static byte[] fillBytes(char c,int len){
        byte[] bytes = new byte[10];
        if (len > 10){
            for (int i = 0; i < 10; i++) {
                bytes[i] = (byte)c;
            }
            return bytes;
        }else {
            for (int i = 0; i < len; i++) {
                bytes[i] = (byte) c;
            }
            for (int i = 9; i >= len; i--) {
                bytes[i] = '_';
            }
        }
        return bytes;
    }
    private static void send() {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);

        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                log.debug("connected");
                ch.pipeline().addLast(new LoggingHandler());
                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        char c = '0';
                        ByteBuf buffer = ctx.alloc().buffer();
                        Random r = new Random();
                        for (int i = 0; i < 10; i++) {
                            byte[] bytes = fillBytes(c, r.nextInt(10) + 1);
                            c++;
                            buffer.writeBytes(bytes);
                        }
                        ctx.writeAndFlush(buffer);
                    }
                });
            }
        });
        ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("localhost", 8000));
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                Channel channel = future.channel();
//                channel
                log.debug("{}",channel);
            }
        });
        channelFuture.channel().closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                group.shutdownGracefully();
            }
        });
    }
}
