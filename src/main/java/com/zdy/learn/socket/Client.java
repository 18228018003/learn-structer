package com.zdy.learn.socket;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/7 21:13
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.setSoTimeout(4000);
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(),8137));

        System.out.println("已发起服务器连接，连接中.......");
        System.out.println("客户端信息: "+socket.getLocalAddress()+" PORT: "+socket.getLocalPort());
        System.out.println("服务端信息: "+socket.getInetAddress()+" PORT: "+socket.getPort());

        try {
            todo(socket);
        }catch (Exception e){
            System.out.println("异常关闭");
        }
        socket.close();
        System.out.println("客户端已经退出");
    }

    private static void todo(Socket client) throws IOException {
        /*键盘输入流*/
        InputStream in = System.in;
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        /*客户端输出流*/
        OutputStream outputStream = client.getOutputStream();
//        PrintStream printStream = new PrintStream(outputStream);

        /*得到服务端的输入  构建输入流*/
        InputStream inputStream = client.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        boolean flag = true;
        do {
            /*键盘读取一行*/
            String readLine = br.readLine();
            /*发送到服务器*/
            outputStream.write(readLine.getBytes());
//            printStream.println(readLine);
            /*从服务器读取一行*/
            String str = reader.readLine();
            if ("bye".equalsIgnoreCase(str)){
                flag = false;
            }else {
                System.out.println(str);
            }
        }while (flag);

    }
}
