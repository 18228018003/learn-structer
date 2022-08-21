package com.zdy.learn.bio.multi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/8 22:08
 */
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket socket = new ServerSocket(9001);
            Socket client = socket.accept();
            InputStream clientInput = client.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientInput));
            String msg;
            while ((msg = reader.readLine()) != null){
                System.out.println("服务器收到："+msg);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
