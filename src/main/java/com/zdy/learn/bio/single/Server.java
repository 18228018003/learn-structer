package com.zdy.learn.bio.single;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/8 21:49
 */
public class Server {
    public static void main(String[] args) {
        try {
            /*1,注册一个ServerSocket对象进行服务端口注册*/
            ServerSocket server = new ServerSocket(9999);
            /*2,监听客户端的socket连接请求*/
            Socket client = server.accept();
            /*3,从client管道中得到字节输入流对象*/
            InputStream clientInput = client.getInputStream();
            /*4,把字节输入包装成为一个缓冲字节输入流*/
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientInput));
            String msg;
            if ((msg = reader.readLine())!=null) {
                System.out.println("服务端接收到："+msg);
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
