package com.mengtu.netty.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import static com.google.common.net.HttpHeaders.CONTENT_LENGTH;

/**
 * 自定义协议要素
 * 魔数：用来在第一时间判断是否是有效数据包
 * 版本号：可以支持协议的升级
 * 序列号算法：消息正文到底采用那种序列化和反序列化方式，可以由此扩展，例如：Json、protobuf、hessian、jdk
 * 指令类型：是登录、注册、单聊、群聊
 * 请求序号：为了双工通信，提升异步能力
 * 正文长度：
 * 消息正文：
 */
@Slf4j
public class TestHttp {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss,worker);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    ch.pipeline().addLast(new HttpServerCodec());
                    //只关注某种特定的消息类型，用SimpleChannelInboundHandler<泛型（关注的消息类型）>
                    ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                            //获取请求
                            log.debug(msg.uri());
                            //返回响应
                            DefaultFullHttpResponse response =
                                    new DefaultFullHttpResponse(msg.protocolVersion(), HttpResponseStatus.OK);
                            byte[] bytes = "<h1>hello Zhang LiJuan !!".getBytes();
                            //告诉浏览器消息长度 让他不要一直转圈
                            response.headers().setInt(CONTENT_LENGTH,bytes.length);
                            response.content().writeBytes(bytes);
                            //写回响应
                            ctx.writeAndFlush(response);
                        }
                    });
                    /*ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            log.debug("{}",msg.getClass());
                        }
                    });*/
                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(8900).sync();
            channelFuture.channel().closeFuture().sync();
            /*channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    Channel channel = future.channel();
                    log.debug("连接成功 do something, channel = {}",channel);
                }
            });*/
            /*channelFuture.channel().closeFuture().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    boss.shutdownGracefully();
                    worker.shutdownGracefully();
                }
            });*/
        }catch (Exception e){
            log.error("client error",e);
        }
    }
}
