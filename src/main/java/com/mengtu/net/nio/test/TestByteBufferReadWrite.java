package com.mengtu.net.nio.test;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

import static com.mengtu.utils.ByteBufferUtil.debugAll;

public class TestByteBufferReadWrite {
    @Test
    public void testRead(){
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 0x61);
        debugAll(buffer);
        buffer.put(new byte[]{0x62,0x63,0x64});
        debugAll(buffer);
    }
    @Test
    public void testAllocate(){
        System.out.println(ByteBuffer.allocate(16).getClass()); //使用java堆内存 读写效率较低 gc影响
        System.out.println(ByteBuffer.allocateDirect(16).getClass()); //使用直接内存  读写效率高(少一次拷贝)
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put(new byte[]{'a','b','c','d'});
        buffer.flip();
        buffer.get(new byte[4]);
        debugAll(buffer);
        buffer.rewind();
        System.out.println((char) buffer.get());
    }
    @Test
    public void testMarkReset(){
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put(new byte[]{'a','b','c','d'});
        buffer.flip();
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
//        buffer.mark();
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
//        buffer.reset();
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get(3));
        debugAll(buffer);
    }

    @Test
    public void testStringTransferToByteBuffer(){
        //1.字符串转ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put("hello".getBytes());
        debugAll(buffer);
        //2.Charset
        ByteBuffer hello = StandardCharsets.UTF_8.encode("hello");
//        ByteBuffer hello = Charset.defaultCharset().encode("hello"); //自动切换到读模式
        debugAll(hello);
        //3.wrap
        ByteBuffer wrap = ByteBuffer.wrap("hello".getBytes());//自动切换到读模式
        debugAll(wrap);

        buffer.flip();
        String string = StandardCharsets.UTF_8.decode(buffer).toString();
        System.out.println(string);
    }

    @Test
    public void testScatteringReads(){
        try (FileChannel channel = new RandomAccessFile("part.txt", "r").getChannel()) {
            ByteBuffer buf1 = ByteBuffer.allocate(3);
            ByteBuffer buf2 = ByteBuffer.allocate(3);
            ByteBuffer buf3 = ByteBuffer.allocate(5);
            channel.read(new ByteBuffer[]{buf1,buf2,buf3});
            buf1.flip();
            buf2.flip();
            buf3.flip();
            debugAll(buf1);
            debugAll(buf2);
            debugAll(buf3);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGatheringWrite(){
        ByteBuffer b1 = StandardCharsets.UTF_8.encode("hello");
        ByteBuffer b2 = StandardCharsets.UTF_8.encode("world");
        ByteBuffer b3 = StandardCharsets.UTF_8.encode("reading");
        try (FileChannel channel = new RandomAccessFile("words.txt","rw").getChannel()){
            channel.write(new ByteBuffer[]{b1,b2,b3});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
