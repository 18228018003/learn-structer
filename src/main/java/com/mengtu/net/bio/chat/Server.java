package com.mengtu.net.bio.chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 目标：BIO模式下的端口转发思想：服务端实现
 *
 * 服务端实现的需求：
 * 1.注册端口
 * 2.接受客户端的Socket连接，交给独立线程来处理
 * 3.把当前连接的客户端socket存入到一个所谓的在线socket集合中保存
 * 4.接收客户端的消息，然后推送给当前所有在线的socket接收
 */
public class Server {
    public static List<Socket> allSocketOnline = new ArrayList<>();
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(9999);
            while (true){
                Socket socket = ss.accept();
                allSocketOnline.add(socket);
                new ServerReaderThread(socket).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
