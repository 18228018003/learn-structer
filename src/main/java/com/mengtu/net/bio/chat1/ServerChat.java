package com.mengtu.net.bio.chat1;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerChat {
    public static Map<Socket,String> onlineSocket = new HashMap<>();
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(Constants.PORT);
            while (true){
                Socket socket = ss.accept();
                new ServerReader(socket).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
