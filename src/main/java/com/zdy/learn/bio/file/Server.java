package com.zdy.learn.bio.file;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/9 0:03
 */
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(9001);
            while (true){
                Socket client = server.accept();
                ServerReaderThread readerThread = new ServerReaderThread(client);
                readerThread.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static class ServerReaderThread extends Thread{
        private Socket socket;
        public ServerReaderThread(Socket socket){
            this.socket = socket;
        }
        @Override
        public void run() {

            try {
                /*得到一个数据输入流读取客户端发送过来的数据*/
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                /*得到文件类型*/
                String suffix = inputStream.readUTF();
                System.out.println("服务端已经接收到了："+suffix);
                FileOutputStream outputStream = new FileOutputStream("D:\\server\\"+ UUID.randomUUID().toString()+suffix);
                /*从数据输入流中读取文件数据，写出到字节输出流中区*/
                byte[] buf = new byte[1024];
                int len;
                while ((len = inputStream.read(buf)) != -1){
                    outputStream.write(buf,0,len);
                }
                outputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
