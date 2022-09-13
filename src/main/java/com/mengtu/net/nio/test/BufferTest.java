package com.mengtu.net.nio.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class BufferTest {
    @Test
    public void testPositionLimitAndFlip(){
        //分配容量
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println("-----------------");
        //往缓冲区写入数据
        String name = "ZhangLJ";
        buffer.put(name.getBytes());
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println("-----------------");
        //切换为读模式
        buffer.flip();
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println("-----------------");
    }

    @Test
    public void testClear(){
        ByteBuffer buffer = ByteBuffer.allocate(10);
        String name = "ZhangLJ";
        buffer.put(name.getBytes());
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println("-----------------");
        buffer.clear();//并没有真正清楚数据
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println(buffer.get());
        System.out.println("-----------------");
    }
    @Test
    public void testMarkReset(){
        ByteBuffer buffer = ByteBuffer.allocate(10);
        String name = "ZhangLJ";
        buffer.put(name.getBytes());
        buffer.flip();
        byte[] bytes = new byte[5];
        buffer.get(bytes);
        String rs = new String(bytes);
        System.out.println(rs);
        System.out.println("-----------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println("-----------------");
        buffer.mark(); //标记此刻的位置
        byte[] b = new byte[2];
        buffer.get(b);
        System.out.println(new String(b));
        System.out.println("-----------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println("-----------------");
        buffer.reset(); //回到标记的位置
        if (buffer.hasRemaining()) {
            System.out.println(buffer.remaining());
        }
    }
    @Test
    public void testAllocate(){
        ByteBuffer buffer = ByteBuffer.allocate(1024); //申请的是堆内存
        System.out.println(buffer.isDirect());//是否是直接内存
    }

    @Test
    public void byteBufferTest(){
        try(FileChannel channel = new FileInputStream("data1.txt").getChannel()){
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true){
                //从channel读取数据向buffer写
                int len = channel.read(buffer);
                if (len == -1){
                    break;
                }
                //打印buffer的内容
                buffer.flip(); //切换读写模式
                while (buffer.hasRemaining()){
                    byte b = buffer.get();
                    System.out.print((char) b + " ");
                }
                buffer.flip();//切换为写模式
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
