package com.mengtu.netty.codec.protocol;

import com.mengtu.netty.codec.message.LoginRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

public class TestMessageCodec {
    public static void main(String[] args) throws Exception {
        LengthFieldBasedFrameDecoder FRAME_DECODER =
                new LengthFieldBasedFrameDecoder(1024*1024, 12, 4, 0, 0);
        LoggingHandler LOGGING_HANDLER = new LoggingHandler();
        EmbeddedChannel channel = new EmbeddedChannel(
                FRAME_DECODER,
                LOGGING_HANDLER,
                new MessageCodec()
        );
        //encode 编码
        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123");
        channel.writeOutbound(message);

        //decode
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null,message,buf);

        //入站
        channel.writeInbound(buf);
    }
}
