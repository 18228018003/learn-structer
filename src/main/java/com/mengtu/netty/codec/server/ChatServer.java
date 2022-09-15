package com.mengtu.netty.codec.server;

import com.mengtu.netty.codec.handler.ChatRequestMessageHandler;
import com.mengtu.netty.codec.handler.GroupCreateRequestMessageHandler;
import com.mengtu.netty.codec.handler.LoginRequestMessageHandler;
import com.mengtu.netty.codec.protocol.MessageCodec;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ChatServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoginRequestMessageHandler LOGIN_HANDLER = new LoginRequestMessageHandler();
        ChatRequestMessageHandler CHAT_REQUEST_HANDLER = new ChatRequestMessageHandler();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.ERROR);
        GroupCreateRequestMessageHandler GROUP_CREATE_MESSAGE = new GroupCreateRequestMessageHandler();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.group(boss,worker);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
//                ch.pipeline().addLast(new ProtocolFrameDecoder());
                ch.pipeline().addLast(loggingHandler);
                ch.pipeline().addLast(new MessageCodec());
                //SimpleChannelInboundHandler 针对特定类型的消息处理器
                ch.pipeline().addLast(LOGIN_HANDLER);
                ch.pipeline().addLast(CHAT_REQUEST_HANDLER);
                ch.pipeline().addLast(GROUP_CREATE_MESSAGE);

            }
        });
        ChannelFuture channelFuture = bootstrap.bind(8000);
        channelFuture.addListener((ChannelFutureListener) future -> {
            Channel channel = future.channel();
            log.debug("{}",channel);
        });
        channelFuture.channel().closeFuture().addListener((ChannelFutureListener) future -> {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        });
    }

}
