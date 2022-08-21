package com.zdy.learn.bio.three;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  目标：实现服务端可以同时接收多个客户端的socket通信请求
 *  思路：是服务端每接收到一个客户端socket请求之后，都交给一个独立的线程
 * @author 周德永
 * @date 2021/11/8 22:19
 */
public class Server {
    public static void main(String[] args) {
        try {
            /*注册端口*/
            ServerSocket server = new ServerSocket(9001);
            /*定义一个死循环，不断接收客户端的socket连接请求*/
            while (true){
                Socket accept = server.accept();
                /*创建独立的线程来处理与这个客户端的socket通信请求*/
                ServerThreadReader threadReader = new ServerThreadReader(accept);
                threadReader.start();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    public static class ServerThreadReader extends Thread{

        private Socket socket;
        public ServerThreadReader(){}
        public ServerThreadReader(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream clientInput = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientInput));
                String msg ;
                while ((msg = reader.readLine()) != null){
                    System.out.println("服务端收到消息："+msg);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
