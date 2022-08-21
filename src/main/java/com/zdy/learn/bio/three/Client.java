package com.zdy.learn.bio.three;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * 客户端：
 *
 * @author 周德永
 * @date 2021/11/8 22:29
 */
public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("127.0.0.1",9001));
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            Scanner sc = new Scanner(System.in);
            while (true){
                String line = sc.nextLine();
                printStream.println(line);
                printStream.flush();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
