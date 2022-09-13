package com.mengtu.net.nio.test;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class TestFileChannel {
    //FileChannel只能工作在阻塞模式下
    @Test
    public void testTransferTo() {
        try (
                FileChannel from = new FileInputStream("data1.txt").getChannel();
                FileChannel to = new FileOutputStream("to.txt").getChannel()
        ){
             long size = from.size();
             //left变量代表还剩余多少字节
             for (long left = size; left > 0;) {
                 left -= from.transferTo(size-left, left, to);
             }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
