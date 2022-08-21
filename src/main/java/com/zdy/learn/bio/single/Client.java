package com.zdy.learn.bio.single;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 客户端代码
 *
 * @author 周德永
 * @date 2021/11/8 21:57
 */
public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("127.0.0.1",9999));
            OutputStream clientOutput = socket.getOutputStream();
            /*将输出流包装为打印流*/
            PrintStream printStream = new PrintStream(clientOutput);
            printStream.print("hello server!");
            printStream.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
