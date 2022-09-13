package com.mengtu.net.bio.three;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端可以接收多个客户端的Socket通信请求
 * 思路：是服务器每接收一个客户端socket请求对象之后都交给一个独立的线程来处理
 */
public class MulitThreadServer {
    public static void main(String[] args) {
        try {
            System.out.println("=============服务器启动=============");
            //1.定义一个serverSocket对象进行服务端的端口注册
            ServerSocket ss = new ServerSocket(8888);
            //2.监听客户端的socket链接请求
            while (true){
                Socket socket = ss.accept();
                new ServerThreadReader(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
