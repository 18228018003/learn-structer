package com.zdy.learn.nio.channel;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/10 20:29
 */
public class ChannelTest {

    @Test
    public void write()
    {

        try {
            /*1.字节输出流通向目标文件*/
            FileOutputStream fos = new FileOutputStream("data01.txt");
            /*2.得到字节输出流对应的通道*/
            FileChannel channel = fos.getChannel();
            /*3.分配缓冲区*/
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put("hello zhoudy welcome to learn nio".getBytes());
            /*4.把缓冲区切换为写模式*/
            buffer.flip();
            channel.write(buffer);
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void read()
    {
        try {
            /*1.字节输入流通向目标文件*/
            FileInputStream fis = new FileInputStream("data01.txt");
            /*2.得到字节输入流对应的通道*/
            FileChannel channel = fis.getChannel();
            /*3.分配缓冲区*/
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            /*4.读取数据到缓冲区*/
            channel.read(buffer);
            /*切换到读模式*/
            buffer.flip();
            /*5.读取缓冲区的数据并输出*/
            String string = new String(buffer.array(), 0, buffer.remaining());
            System.out.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void copyFile()
    {
        try {
            File srcFile = new File("D:\\pic\\VID_20200203_004947.mp4");
            File destFile = new File("D:\\pic\\zlj.mp4");
            FileInputStream fis = new FileInputStream(srcFile);
            FileOutputStream fos = new FileOutputStream(destFile);
            FileChannel fisChannel = fis.getChannel();
            FileChannel fosChannel = fos.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true){
                buffer.clear();
                int read = fisChannel.read(buffer);
                if (read == -1) break;
                buffer.flip();
                fosChannel.write(buffer);
            }
            fisChannel.close();
            fosChannel.close();
            System.out.println("复制完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*分散和聚集*/
    @Test
    public void test() throws Exception{
        /*字节输入流管道*/
        FileInputStream fis = new FileInputStream("data1.txt");
        /*字节输出流管道*/
        FileOutputStream fos = new FileOutputStream("data2.txt");
        /*分配多个缓存区做数据分散*/
        ByteBuffer buffer1 = ByteBuffer.allocate(4);
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);
        ByteBuffer[] buffers = {buffer1,buffer2};
        /*从通道中读取数据，分散到缓存区*/
        FileChannel fisChannel = fis.getChannel();
        FileChannel fosChannel = fos.getChannel();
        fisChannel.read(buffers);
        for (ByteBuffer buffer : buffers) {
            buffer.flip();
        }
        fosChannel.write(buffers);
        fisChannel.close();
        fosChannel.close();
        System.out.println("文件复制完成");
    }

    @Test
    public void test01()
    {
        try {
            /*字节输入流管道*/
            FileInputStream fis = new FileInputStream("data1.txt");
            FileChannel fisChannel = fis.getChannel();
            /*字节输出流管道*/
            FileOutputStream fos = new FileOutputStream("data3.txt");
            FileChannel fosChannel = fos.getChannel();

            /*复制数据*/
            fosChannel.transferFrom(fisChannel,fisChannel.position(),fisChannel.size());
            fisChannel.close();
            fosChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
