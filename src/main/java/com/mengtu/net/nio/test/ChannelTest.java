package com.mengtu.net.nio.test;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelTest {
    @Test
    public void write(){
        try {
            //1.字节输出流
            FileOutputStream fos = new FileOutputStream("data01.txt");
            //2.得到字节输出流对应的通道
            FileChannel channel = fos.getChannel();
            //3.分配缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);
            buf.put("hello,黑马程序员".getBytes());
            //4.把缓冲区切换为写出模式
            buf.flip();
            channel.write(buf);
            channel.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void read(){
        try {
            FileInputStream fis = new FileInputStream("data1.txt");
            FileChannel channel = fis.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(1024);
            channel.read(buf);
            buf.flip();
            System.out.println(new String(buf.array(),0,buf.remaining()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void copy() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            File file = new File("D:\\pic\\zlj.mp4");
            //得到一个字节输出流和字节输出流
            fis = new FileInputStream(file);
            fos = new FileOutputStream("D:\\log\\zlj.mp4");
            //得到文件通道
            FileChannel fisChannel = fis.getChannel();
            FileChannel fosChannel = fos.getChannel();
            //分配缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);
            while (true){
                //先清空缓冲区
                buf.clear();
                //开始读取数据
                int flag = fisChannel.read(buf);
                if (flag == -1){
                    break;
                }
                //已经读取了数据 把缓冲区切换为读模式
                buf.flip();
                fosChannel.write(buf);
                fisChannel.close();
                fosChannel.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test1() throws IOException {
        //1.字节输入管道
        FileInputStream fis = new FileInputStream("data1.txt");
        FileChannel fisChannel = fis.getChannel();
        //2.字节输出管道
        FileOutputStream fos = new FileOutputStream("data2.txt");
        FileChannel fosChannel = fos.getChannel();
        //3.定义多个缓冲区
        ByteBuffer buf = ByteBuffer.allocate(6);
        ByteBuffer buf1 = ByteBuffer.allocate(1024);
        ByteBuffer[] bufs = {buf,buf1};
        //4.从通道中读取数据，分散到各个缓冲区
        fisChannel.read(bufs);
        //5.从每个缓冲区中查询是否有数据读取到
        for (ByteBuffer buff : bufs) {
            buff.flip();//切换到读模式
            System.out.println(new String(buff.array(),0,buff.remaining()));
        }
        fosChannel.write(bufs);
        fis.close();
        System.out.println("复制完成");
    }

    @Test
    public void transferTo() throws IOException {
        FileInputStream fis = new FileInputStream("data1.txt");
        FileChannel fisChannel = fis.getChannel();
        FileOutputStream fos = new FileOutputStream("data2.txt");
        FileChannel fosChannel = fos.getChannel();
        fisChannel.transferTo(fisChannel.position(), fisChannel.size(),fosChannel);
        fisChannel.close();
        fosChannel.close();
    }
    @Test
    public void transferFrom() throws IOException {
        FileInputStream fis = new FileInputStream("data1.txt");
        FileChannel fisChannel = fis.getChannel();
        FileOutputStream fos = new FileOutputStream("data2.txt");
        FileChannel fosChannel = fos.getChannel();
        fosChannel.transferFrom(fisChannel, fisChannel.position(),fisChannel.size());
        fisChannel.close();
        fosChannel.close();
    }
}
