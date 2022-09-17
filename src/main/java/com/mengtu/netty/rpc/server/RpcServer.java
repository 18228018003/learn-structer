package com.mengtu.netty.rpc.server;

import com.mengtu.netty.codec.protocol.MessageCodec;
import com.mengtu.netty.codec.protocol.ProtocolFrameDecoder;
import com.mengtu.netty.rpc.handler.RpcRequestMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcServer {
    public static void main(String[] args) throws InterruptedException {
        //各种handler
        MessageCodec MESSAGE_CODEC = new MessageCodec();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler();
        RpcRequestMessageHandler RPC_HANDLER = new RpcRequestMessageHandler();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.group(boss,worker);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ProtocolFrameDecoder());
                ch.pipeline().addLast(LOGGING_HANDLER);
                ch.pipeline().addLast(MESSAGE_CODEC);
                ch.pipeline().addLast(RPC_HANDLER);
            }
        });
        ChannelFuture channelFuture = bootstrap.bind(9000);
        channelFuture.sync().channel().closeFuture().sync();
    }
}
