package com.zdy.learn.bio.file;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 *  目标：实现客户端上传任意类型的文件数据给服务器保存起来
 * @author 周德永
 * @date 2021/11/8 23:16
 */
public class Client {
    public static void main(String[] args) {
        try {
            /*请求与服务端连接*/
            Socket socket = new Socket("127.0.0.1",9001);
            /*把字节输出包装成一个数据输出流*/
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(outputStream);
            /*先发送上传文件的后缀发送给服务端*/
            dos.writeUTF(".jpeg");
            /*把文件数据发送给服务端进行接受*/
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\周德永.DESKTOP-9U0HPFE.000\\Downloads\\8facb1f46ece706e22f85900f5bd8aa4.jpeg");
            byte[] buffer = new byte[1024];
            int len;
            while ((len=fileInputStream.read(buffer)) != -1){
                dos.write(buffer,0,len);
            }
            dos.flush();
            socket.shutdownOutput();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
