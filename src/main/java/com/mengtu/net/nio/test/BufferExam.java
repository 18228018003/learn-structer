package com.mengtu.net.nio.test;

import org.junit.Test;

import java.nio.ByteBuffer;

import static com.mengtu.utils.ByteBufferUtil.debugAll;

public class BufferExam {
    @Test
    public void test(){
        ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(buffer);
        buffer.put("w are you\n".getBytes());
        split(buffer);
    }

    private void split(ByteBuffer buffer) {
        buffer.flip();//切换为读模式
        for (int i = 0; i < buffer.limit(); i++) {
            //找到完整消息
            if (buffer.get(i) == '\n') {
                int length = i + 1 - buffer.position();
                //将这条完整消息存入新的ByteBuffer
                ByteBuffer target = ByteBuffer.allocate(length);
                //从buffer读向target写
                for (int j = 0; j < length; j++) {
                    target.put(buffer.get());
                }
                debugAll(target);
            }
        }
        buffer.compact();//不能用clear 会把上次为处理完的消息丢弃
    }
}
