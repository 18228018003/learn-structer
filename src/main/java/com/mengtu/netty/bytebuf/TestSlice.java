package com.mengtu.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static com.mengtu.utils.ByteBufferUtil.log;

public class TestSlice {
    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});
        log(buf);
        //在切片过程中并没有发生数据复制
        ByteBuf f1 = buf.slice(0, 5);
        f1.retain();
        ByteBuf f2 = buf.slice(5, 5);
        f2.retain();
        log(f1);
        log(f2);
        //切片之后 f1 f2 不能在写入其他数据
        System.out.println("===============");
        f1.setByte(0,'b');
        log(f1);
        log(buf);

//        System.out.println("释放原有内存");
//        buf.release(); //释放原有buf之后会影响切片出来的buf  解决  使用retain
//        log(f1);
    }
}
