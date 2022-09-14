package com.mengtu.netty.stickingwrapping;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TestLengthFieldBaseFrameDecoder {
    public static void main(String[] args) {

        EmbeddedChannel channel = new EmbeddedChannel(
                /**
                 * 解决办法4.基于长度字段的帧解码器
                 *  参数：lengthFieldOffset 长度字段偏移   记录消息长度的偏移地址
                 *  参数：lengthFieldLength 长度字段长度   消息长度是多少字节
                 *  参数：initialBytesToStrip 从头剥离几个字节
                 *  参数：lengthAdjustment 长度字段为基准 还有几个字节是内容
                 */
                new LengthFieldBasedFrameDecoder(
                        1024,0,4,1,4),
                new LoggingHandler(LogLevel.DEBUG)
        );
        //4个字节的长度  实际内容
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        send(buffer, "hello, world");
        send(buffer, "hi");
        channel.writeInbound(buffer);
    }

    private static void send(ByteBuf buffer, String content) {
        byte[] bytes = content.getBytes();
        int length = bytes.length; //实际内容长度
        buffer.writeInt(length);
        buffer.writeByte(1);
        buffer.writeBytes(bytes);
    }
}
