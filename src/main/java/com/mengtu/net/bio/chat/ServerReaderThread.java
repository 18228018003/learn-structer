package com.mengtu.net.bio.chat;

import java.io.*;
import java.net.Socket;

public class ServerReaderThread extends Thread{
    private Socket socket;

    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //1.从socket中获取当前客户端的输入流
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String msg;
            while ((msg = br.readLine()) != null){
                //2.接收到了客户端的消息，推送给所有在线的socket
                sendToAllClient(msg);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("有人下线");
            Server.allSocketOnline.remove(socket);
        }
    }

    //把当前客户端发来的消息推送给所有在线socket
    private void sendToAllClient(String msg) throws IOException {
        for (Socket socket : Server.allSocketOnline) {
            PrintStream ps = new PrintStream(socket.getOutputStream());
            ps.println(msg);
            ps.flush();
        }
    }
}
