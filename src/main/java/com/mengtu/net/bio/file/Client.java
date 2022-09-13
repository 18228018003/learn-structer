package com.mengtu.net.bio.file;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * 实现客户端上传任意类型的文件数据给服务器保存起来
 */
public class Client {
    public static void main(String[] args) {
        try {
            //1.请求与服务端的Socket连接
            Socket socket = new Socket("127.0.0.1",9999);
            //2.把字节输出流包装成一个数据输出流
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            //3.先发送上传文件的后缀给服务器
            dos.writeUTF(".mp4");
            //4.把文件数据发送给服务端进行接受
            InputStream is = new FileInputStream("D:\\pic\\zlj.mp4");
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1){
                dos.write(buffer,0,len);
            }
            dos.flush();
            socket.shutdownOutput();//通知服务端数据发送完毕
            is.close();
            dos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
