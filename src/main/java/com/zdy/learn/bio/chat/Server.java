package com.zdy.learn.bio.chat;

import com.zdy.learn.bio.chat.util.Constant;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * BIO模式下的端口转发思想-服务器实现
 *
 * 服务端实现的需求
 *  1.注册端口
 *  2.接受客户端的socket连接，交给一个独立的线程来处理
 *  3.把当前连接的客户端socket存入一个所谓的在线socket集合中保存
 *  4.接受客户端的消息，推送给所有在线的socket接受
 *
 * @author 周德永
 * @date 2021/11/9 22:11
 */
public class Server {

    /*定义一个静态集合*/
    public static Map<Socket,String> onlineSockets = new HashMap<>();

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(Constant.PORT);
            while (true){
                Socket client = server.accept();
                /*把登录的客户端socket存入到一个在线集合里面去*/
//                onlineSockets.add(client);

                /*为当前登录成功的socket分配一个独立的线程来处理与之通信*/
                new ServerReaderThread(client).start();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
