package com.zdy.learn.bio.chat;

import com.zdy.learn.bio.chat.util.Constant;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/9 22:16
 */
public class ServerReaderThread extends Thread{
    private Socket socket;
    public ServerReaderThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(socket.getInputStream());
            /*循环一直等待客户端消息*/
            while (true){
                /*读取当前的消息类型：登录，群发，私聊，@消息*/
                int flag = dis.readInt();
                if (flag == 1){
                    /*先将当前登录的客户端socket存到在线人数的socket集合当中去*/
                    String name = dis.readUTF();
                    System.out.println(name+"----->"+socket.getRemoteSocketAddress());
                    Server.onlineSockets.put(socket,name);
                }
                writeMsg(flag,dis);
            }

        } catch (IOException ioException) {
            System.out.println("有客户端下线了");
            /*从在线人数中将当前socket移出去*/
            Server.onlineSockets.remove(socket);
            try {
                /*更新在线人数，并发送给所有客户端*/
                writeMsg(1,dis);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void writeMsg(int flag, DataInputStream dis) {
        /*定义变量 表示消息形式*/
        String msg = null;
        if (flag == 1){
            /*读取所有在线人数并发给所有客户端去更新自己的在线人数列表*/
            StringBuilder rs = new StringBuilder();
            Collection<String> onlineNames = Server.onlineSockets.values();
            if (onlineNames != null && onlineNames.size() > 0){
                for (String name : onlineNames) {
                    rs.append(name).append(Constant.SPILIT);
                }
                msg = rs.substring(0,rs.lastIndexOf(Constant.SPILIT));
            }
            sendMsgToAll(flag,msg);
        }else if (flag == 2 || flag==3){
            /*读取消息 群发或者@消息*/
            try {
                String newMsg = dis.readUTF();
                String sendName = Server.onlineSockets.get(socket);
                /*内容*/
                StringBuilder msgFinal = new StringBuilder();
                /*时间*/
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE");
                if (flag == 2){
                    msgFinal.append(sendName).append("  ").append(sdf.format(System.currentTimeMillis()*2)).append("\r\n");
                    msgFinal.append("  ").append(newMsg).append("\r\n");
                    sendMsgToAll(flag,msgFinal.toString());
                }else if(flag == 3){
                    msgFinal.append(sendName).append("  ").append(sdf.format(System.currentTimeMillis()*2)).append("\r\n");
                    msgFinal.append("  ").append(newMsg).append("\r\n");
                    /*私发*/
                    String destName = dis.readUTF();
                    sendMsgToOne(destName,msgFinal.toString());
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void sendMsgToOne(String destName, String msg) throws IOException {
        for (Socket client : Server.onlineSockets.keySet()) {
            /*得到当前需要私发的socket*/
            if (Server.onlineSockets.get(client).trim().equals(destName)){
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                dos.writeUTF(msg);
                dos.writeInt(2);/*消息类型*/
                dos.flush();
            }
        }
    }

    /**
     * 把当前客户端发来的消息推送给全部在线的socket
     * @param msg 消息
     * @param flag 消息类型
     */
    private void sendMsgToAll(int flag,String msg) {
        for (Socket clint : Server.onlineSockets.keySet()) {
            try {
                DataOutputStream dos = new DataOutputStream(clint.getOutputStream());
                dos.writeInt(flag);
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
