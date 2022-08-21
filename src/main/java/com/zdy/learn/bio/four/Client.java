package com.zdy.learn.bio.four;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/8 22:13
 */
public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("127.0.0.1",9001));
            OutputStream outputStream = socket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            Scanner sc = new Scanner(System.in);
            while (true){
                System.out.println("请输入：");
                String msg = sc.nextLine();
                printStream.println(msg);
                printStream.flush();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
