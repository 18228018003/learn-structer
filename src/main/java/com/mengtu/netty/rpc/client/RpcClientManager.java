package com.mengtu.netty.rpc.client;

import com.mengtu.netty.codec.protocol.MessageCodecSharable;
import com.mengtu.netty.codec.protocol.ProtocolFrameDecoder;
import com.mengtu.netty.protocol.SequenceIdGenerator;
import com.mengtu.netty.rpc.handler.RpcResponseMessageHandler;
import com.mengtu.netty.rpc.message.RpcRequestMessage;
import com.mengtu.netty.rpc.service.HelloService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;

@Slf4j
public class RpcClientManager {

    public static void main(String[] args) {
        HelloService service = getProxyService(HelloService.class);
        System.out.println(service.sayHello("张三"));
        System.out.println(service.sayHello("李四"));
        System.out.println(service.sayHello("王五"));
    }

    //拿到代理对象
    public static <T> T getProxyService(Class<T> serviceClass){
        ClassLoader loader = serviceClass.getClassLoader();
        Class<?>[] interfaces = new Class[]{serviceClass};
        Object o = Proxy.newProxyInstance(loader, interfaces, (proxy, method, args) -> {
            //1.将方法调用转换为消息对象
            int sequenceId = SequenceIdGenerator.nextId();
            RpcRequestMessage message = new RpcRequestMessage(
                    sequenceId,
                    serviceClass.getName(),
                    method.getName(),
                    method.getReturnType(),
                    method.getParameterTypes(),
                    args);
            //2.将消息对象发送出去
            getChannel().writeAndFlush(message);
            /*3准备一个promise对象 来接收结果                  指定promise对象接受结果的线程*/
            DefaultPromise<Object> promise = new DefaultPromise<>(getChannel().eventLoop());
            RpcResponseMessageHandler.PROMISE_MAP.put(sequenceId,promise);
//            promise.sync();//会抛异常
            /*4.等待promise结果*/
            promise.await(); //不会抛异常
            if (promise.isSuccess()) {
                //调用正常
                return promise.getNow();
            }else {
                //出现异常
                throw new RuntimeException(promise.cause());
            }
            //3.暂时返回null
        });
        return (T) o;
    }

    private static Channel channel = null;

    private static final Object LOCK = new Object();
    /* 获取唯一channel对象 */
    public static Channel getChannel(){
        if (channel != null) return channel;
        synchronized (LOCK){
            if (channel != null){
                return channel;
            }
            initChannel();
            return channel;
        }
    }

    //初始化channel
    private static void initChannel() {
        NioEventLoopGroup group = new NioEventLoopGroup();
//        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        RpcResponseMessageHandler RPC_HANDLER = new RpcResponseMessageHandler();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(group);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ProtocolFrameDecoder());
//                ch.pipeline().addLast(LOGGING_HANDLER);
                ch.pipeline().addLast(MESSAGE_CODEC);
                ch.pipeline().addLast(RPC_HANDLER);
            }
        });
        try {
            channel = bootstrap.connect("localhost", 9000).sync().channel();
            channel.closeFuture().addListener(future -> {
                System.err.println(Thread.currentThread().getName());;
                group.shutdownGracefully();
            });
        } catch (Exception e) {
            log.error("client error", e);
        }
    }
}
