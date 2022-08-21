package com.zdy.learn.netty.buffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/11 23:42
 */
public class TestByteBuffer {
    public static void main(String[] args) {
        try (FileChannel fisChannel = new FileInputStream("data1.txt").getChannel())
        {
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true){

                int len = fisChannel.read(buffer);

                if (len == -1) break;
                buffer.flip();
                while (buffer.hasRemaining()){
                    byte b = buffer.get();
                    System.out.println((char) b);
                }
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
