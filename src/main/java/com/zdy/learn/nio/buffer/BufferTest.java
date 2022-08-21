package com.zdy.learn.nio.buffer;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/9 23:33
 */
public class BufferTest {
    @Test
    public void test01(){
        /*1.分配一个缓冲区，容量设置为10*/
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println(buffer.position());  //0
        System.out.println(buffer.limit());     //10
        System.out.println(buffer.capacity());  //10
        System.out.println("====================");
        /*往缓冲区中添加数据*/
        String name = "itheima";
        buffer.put(name.getBytes());
        System.out.println(buffer.position());  //7
        System.out.println(buffer.limit());     //10
        System.out.println(buffer.capacity());  //10
        System.out.println("====================");
        /*flip() 将缓冲区的界限设置为当前位置，并将当前位置设置为0*/
        buffer.flip();
        System.out.println(buffer.position());  //0
        System.out.println(buffer.limit());     //7
        System.out.println(buffer.capacity());  //10

        System.out.println("====================");
        char b = (char) buffer.get();
        System.out.println(b);
        System.out.println(buffer.position());  //1
        System.out.println(buffer.limit());     //7
        System.out.println(buffer.capacity());  //10

    }

    @Test
    public void test02()
    {
        /*1.分配一个缓冲区，容量设置为10*/
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println(buffer.position());  //0
        System.out.println(buffer.limit());     //10
        System.out.println(buffer.capacity());  //10
        System.out.println("====================");
        /*往缓冲区中添加数据*/
        String name = "itheima";
        buffer.put(name.getBytes());
        System.out.println(buffer.position());  //7
        System.out.println(buffer.limit());     //10
        System.out.println(buffer.capacity());  //10
        System.out.println("====================");

        buffer.clear();

        System.out.println(buffer.position());  //0
        System.out.println(buffer.limit());     //10
        System.out.println(buffer.capacity());  //10
        System.out.println((char) buffer.get());
        System.out.println("====================");
    }
}
