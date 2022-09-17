package com.mengtu.netty.codec.client;

import com.mengtu.netty.codec.message.*;
import com.mengtu.netty.codec.protocol.MessageCodec;
import com.mengtu.netty.codec.protocol.ProtocolFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ChatClient {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        CountDownLatch WAIT_FOR_LOGIN = new CountDownLatch(1);
        AtomicBoolean LOGIN = new AtomicBoolean(false);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(group);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {

                ch.pipeline().addLast(new ProtocolFrameDecoder());
//                ch.pipeline().addLast(new LoggingHandler(LogLevel.ERROR));
                ch.pipeline().addLast(new MessageCodec());
                //5s内如果没有向服务器写出数据, 会触发一个事件IdleState.WRITE_IDLE事件
                ch.pipeline().addLast(new IdleStateHandler(0,5,0));
                //new ChannelDuplexHandler() 即可以作为入站处理器也可以作为出站处理器
                ch.pipeline().addLast(new ChannelDuplexHandler(){
                    //用来触发特殊事件
                    @Override
                    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                        IdleStateEvent event = (IdleStateEvent) evt;
                        //触发了读空闲事件
                        if (event.state() == IdleState.WRITER_IDLE){
                            log.debug("已经3秒没有写数据了 自动发送一个心跳包");
                            ctx.writeAndFlush(new PingMessage());
                        }
                        super.userEventTriggered(ctx, evt);
                    }
                });
                ch.pipeline().addLast("client handler",new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        log.debug("msg: {}",msg);
                        if(msg instanceof LoginResponseMessage){
                            LoginResponseMessage response = (LoginResponseMessage)msg;
                            if (response.isSuccess()) {
                                //如果登录成功
                                LOGIN.set(true);
                            }
                            //唤醒system in线程
                            WAIT_FOR_LOGIN.countDown();
                        }
                    }

                    //在连接建立后触发active事件
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        //负责接收用户在控制台的输入，负责向服务器发送各种消息
                        new Thread(()->{
                            log.info("请输入用户名：");
                            Scanner scanner = new Scanner(System.in);
                            String username = scanner.nextLine();
                            log.info("请输入密码：");
                            String password = scanner.nextLine();
                            //构造消息对象
                            LoginRequestMessage message = new LoginRequestMessage(username, password);
                            //发送消息
                            ctx.writeAndFlush(message);
                            log.info("等待后续操作：");
                            try {
                                WAIT_FOR_LOGIN.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //如果登录失败
                            if (!LOGIN.get()) {
                                ctx.channel().close();
                                return;
                            }
                            while (true){
                                System.out.println("==================================");
                                System.out.println("send [username] [content]");
                                System.out.println("gsend [group name] [content]");
                                System.out.println("gcreate [group name] [m1,m2,m3...]");
                                System.out.println("gmembers [group name]");
                                System.out.println("gjoin [group name]");
                                System.out.println("gquit [group name]");
                                System.out.println("quit");
                                System.out.println("==================================");
                                String command = null;
                                try {
                                    command = scanner.nextLine();
                                } catch (Exception e) {
                                    break;
                                }
                                String[] s = command.split(" ");
                                switch (s[0]){
                                    case "send":
                                        ctx.writeAndFlush(new ChatRequestMessage(username, s[1], s[2]));
                                        break;
                                    case "gsend":
                                        ctx.writeAndFlush(new GroupChatRequestMessage(username, s[1], s[2]));
                                        break;
                                    case "gcreate":
                                        Set<String> set = new HashSet<>(Arrays.asList(s[2].split(",")));
                                        set.add(username); // 加入自己
                                        ctx.writeAndFlush(new GroupCreateRequestMessage(s[1], set));
                                        break;
                                    case "gmembers":
                                        ctx.writeAndFlush(new GroupMembersRequestMessage(s[1]));
                                        break;
                                    case "gjoin":
                                        ctx.writeAndFlush(new GroupJoinRequestMessage(username, s[1]));
                                        break;
                                    case "gquit":
                                        ctx.writeAndFlush(new GroupQuitRequestMessage(username, s[1]));
                                        break;
                                    case "quit":
                                        ctx.channel().close();
                                        return;
                                }
                            }
                        },"system in").start();
                    }

                    //连接断开时触发
                    @Override
                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                        super.channelInactive(ctx);
                    }

                    //出现异常时触发
                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                        super.exceptionCaught(ctx, cause);
                    }
                });
            }
        });
        ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("localhost", 8000));
        channelFuture.addListener((ChannelFutureListener) future -> System.out.println(future.channel()));
        channelFuture.channel().closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                group.shutdownGracefully();
            }
        });
    }
}
