package com.learn.netty;

import com.mengtu.netty.codec.message.LoginRequestMessage;
import com.mengtu.netty.codec.protocol.MessageCodec;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;

public class TestSerializer {
    public static void main(String[] args) {
        MessageCodec codec = new MessageCodec();
        LoggingHandler log = new LoggingHandler();
        EmbeddedChannel channel = new EmbeddedChannel(log, codec, log);
        LoginRequestMessage message = new LoginRequestMessage("张三", "123");
        channel.writeOutbound(message);
    }
}
