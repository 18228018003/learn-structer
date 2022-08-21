package com.zdy.learn.socket;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/7 21:13
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8137);

        System.out.println("服务器准备就绪");
        System.out.println("服务器信息："+server.getInetAddress()+"port: "+server.getLocalPort());

        /*等待客户端连接*/
        for (;;){
            /*得到客户端*/
            Socket client = server.accept();
            /*构建异步线程*/
            ClientHandler clientHandler = new ClientHandler(client);
            /*启动线程*/
            clientHandler.start();
        }
    }
    /*客户端的消息处理*/
    private static class ClientHandler extends Thread{
        private Socket socket;
        private boolean flag = true;

        ClientHandler(Socket socket){
            this.socket = socket;
        }

        @SneakyThrows
        @Override
        public void run() {
            System.out.println("新客户端连接："+socket.getInetAddress() + " port: "+socket.getPort());
            try {
                /*得到打印流，用户数据输出；服务器回送数据*/
                PrintStream socketOutput = new PrintStream(socket.getOutputStream());
                /*得到输入流，用于接受客户端数据*/
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                do {
                    String clientData = socketInput.readLine();
                    if ("bye".equalsIgnoreCase(clientData)){
                        flag = false;
                        /*回送数据*/
                        socketOutput.println("bye");
                    }else {
                        /*打印到屏幕，并回送数据长度*/
                        System.out.println(clientData);
                        socketOutput.println("回送："+clientData.length());
                    }
                }while (flag);
            }catch (Exception e){
                System.out.println("连接异常断开");
            }finally {
                socket.close();
            }
            System.out.println("客户端以退出："+socket.getInetAddress() + " Port: "+socket.getPort());
        }
    }
    private static void todo(Socket client) {


    }
}
