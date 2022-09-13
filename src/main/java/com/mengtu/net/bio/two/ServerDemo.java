package com.mengtu.net.bio.two;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 客户端反复发送消息
 * 服务端反复接收消息
 */
public class ServerDemo {
    public static void main(String[] args) {
        try {
            System.out.println("=============服务器启动=============");
            //1.定义一个serverSocket对象进行服务端的端口注册
            ServerSocket ss = new ServerSocket(8888);
            //2.监听客户端的socket链接请求
            Socket socket = ss.accept();
            //3.从socket管道中得到一个字节输入流对象
            InputStream is = socket.getInputStream();
            //4.把字节输入流包装为一个缓冲字符输入流
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
            String msg;
            while ((msg = bf.readLine()) != null){
                System.out.println("服务端接收到：" + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
