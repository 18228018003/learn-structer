package com.mengtu.netty.rpc.handler;

import com.mengtu.netty.rpc.message.RpcResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ChannelHandler.Sharable
public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {
    /*                       序列号    容器 */
    public static final Map<Integer, Promise<Object>> PROMISE_MAP = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponseMessage msg) throws Exception {
        //拿到空的promise
        Promise<Object> promise = PROMISE_MAP.remove(msg.getSequenceId());
        //泛型通配符? 只能从中获取值 不能设置值
//        Promise<?> promise = PROMISE_MAP.get(msg.getSequenceId());
        if (promise != null) {
            Object returnValue = msg.getReturnValue();
            Exception exceptionValue = msg.getExceptionValue();
            if (exceptionValue != null){
                promise.setFailure(exceptionValue);
            }else {
                promise.setSuccess(returnValue);
            }

        }
    }
}
