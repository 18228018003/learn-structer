package com.mengtu.netty.codec.server;

import com.mengtu.netty.codec.handler.ChatRequestMessageHandler;
import com.mengtu.netty.codec.handler.GroupCreateRequestMessageHandler;
import com.mengtu.netty.codec.handler.LoginRequestMessageHandler;
import com.mengtu.netty.codec.protocol.MessageCodec;
import com.mengtu.netty.codec.protocol.ProtocolFrameDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ChatServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoginRequestMessageHandler LOGIN_HANDLER = new LoginRequestMessageHandler();
        ChatRequestMessageHandler CHAT_REQUEST_HANDLER = new ChatRequestMessageHandler();
//        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.ERROR);
        GroupCreateRequestMessageHandler GROUP_CREATE_MESSAGE = new GroupCreateRequestMessageHandler();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.group(boss,worker);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                ch.pipeline().addLast(new ProtocolFrameDecoder());
//                ch.pipeline().addLast(loggingHandler);
                ch.pipeline().addLast(new MessageCodec());
                //用來判断是不是 读空闲时间过长 或写空闲时间过长
                //5s内如果没有收到channel的数据, 会触发一个事件IdleState.READER_IDLE事件
                ch.pipeline().addLast(new IdleStateHandler(10,0,0));
                //new ChannelDuplexHandler() 即可以作为入站处理器也可以作为出站处理器
                ch.pipeline().addLast(new ChannelDuplexHandler(){
                    //用来触发特殊事件
                    @Override
                    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                        IdleStateEvent event = (IdleStateEvent) evt;
                        //触发了读空闲事件
                        if (event.state() == IdleState.READER_IDLE){
                            log.debug("已经5秒读不到数据了");
                            ctx.channel().close();
                        }
                        super.userEventTriggered(ctx, evt);
                    }
                });
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
