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
 * 解决办法4.基于长度字段的帧解码器
 *  参数：lengthFieldOffset 长度字段偏移   记录消息长度的偏移地址
 *  参数：lengthFieldLength 长度字段长度   消息长度是多少字节
 *  参数：initialBytesToStrip 从头剥离几个字节
 *  参数：lengthAdjustment 长度字段为基准 还有几个字节是内容
 */
@Slf4j
public class LengthFieldBaseFrameDecoderClient {
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

    public static StringBuilder makeString(char c,int len){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(c);
        }
        sb.append('\n');
        return sb;
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
                            StringBuilder sb = makeString(c, r.nextInt(256) + 1);
                            c++;
                            buffer.writeBytes(sb.toString().getBytes());
                        }
                        ctx.writeAndFlush(buffer);
                        ctx.channel().close();
                    }
                });
            }
        });
        ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("localhost", 8000));
        channelFuture.addListener((ChannelFutureListener) future -> {
            Channel channel = future.channel();
//                channel
            log.debug("{}",channel);
        });
        channelFuture.channel().closeFuture().addListener((ChannelFutureListener) future -> group.shutdownGracefully());
    }
}
