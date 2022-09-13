package com.mengtu.net.bio.file;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ServerReaderThread extends Thread{
    private Socket socket;

    public ServerReaderThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String suffix = dis.readUTF();
            System.out.println("服务端已经接收到了文件类型："+suffix);
            OutputStream os = new FileOutputStream("D:\\log\\ZhuLiJuan"+ suffix);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = dis.read(buffer)) != -1){
                os.write(buffer,0,len);
            }
            os.close();
            System.out.println("文件保存成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
