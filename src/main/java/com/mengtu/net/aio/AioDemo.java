package com.mengtu.net.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static com.mengtu.utils.ByteBufferUtil.debugAll;

public class AioDemo {
    public static void main(String[] args) {
        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("data1.txt"), StandardOpenOption.READ)) {
            //参数1 ByteBuffer
            //参数2 读取的起始位置
            //参数3 附件
            //参数4 回调对象
            ByteBuffer buffer = ByteBuffer.allocate(16);
            System.out.println(Thread.currentThread()+" read begin ");
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override   //read成功  result读到的实际字节数  attachment 就是传入的附件
                public void completed(Integer result, ByteBuffer attachment) {
                    System.out.println(Thread.currentThread()+" read completed " + result);
                    attachment.flip();
                    debugAll(attachment);
                }

                @Override   //read失败
                public void failed(Throwable exc, ByteBuffer attachment) {
                    System.out.println(exc.getMessage());
                }
            });
            System.out.println(Thread.currentThread()+" read finished ");
            System.in.read();
        } catch (IOException e) {
        }
    }
}
