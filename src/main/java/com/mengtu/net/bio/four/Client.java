package com.mengtu.net.bio.four;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

//客户端
public class Client {
    public static void main(String[] args) {
        try {
            //1.创建socket对象请求服务端的链接
            Socket socket = new Socket("127.0.0.1",8888);
            //2.从socket对象中获取一个字节输出流
            OutputStream os = socket.getOutputStream();
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            //3.把字节输出流包装为打印流
            PrintStream ps = new PrintStream(os);
            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.println("请输入：");
                String msg = scanner.nextLine();
                ps.println(msg);
                ps.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
