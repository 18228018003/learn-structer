package com.mengtu.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

import static com.mengtu.utils.ByteBufferUtil.log;

@Slf4j
public class TestByteBuf {
    public static void main(String[] args) {
        //不指定容量  默认256
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        log(buf);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            sb.append("a");
        }
        buf.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8));
        log(buf);
    }
}
