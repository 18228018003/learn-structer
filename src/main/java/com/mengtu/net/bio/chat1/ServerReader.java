package com.mengtu.net.bio.chat1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Set;

public class ServerReader extends Thread{
    private Socket socket;

    public ServerReader(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(socket.getInputStream());
            //1.循环一直等待客户端的消息
            while (true){
                //2.读取当前消息的类型：登录,群发,私聊,@消息
                int flag = dis.readInt();
                if (flag == 1){
                    //3.将当前登录的客户端socket存到在线人数的socket集合当中
                    String name = dis.readUTF();
                    System.out.println(name + "--->" + socket.getRemoteSocketAddress());
                    ServerChat.onlineSocket.put(socket,name);
                }
                writeMsg(flag,dis);
            }
        }catch (Exception e){
            System.out.println("有人下线了");
            ServerChat.onlineSocket.remove(socket);
            try {
                //更新在线人数并发送给所有客户端
                writeMsg(1,dis);
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
    }

    private void writeMsg(int flag, DataInputStream dis) throws IOException {
        String msg = null;
        /*读取所有在线人数发送给所有客户端去更新自己的在线人数列表*/
        StringBuilder rs = new StringBuilder();
        if (flag == 1){
            Collection<String> onlineNames = ServerChat.onlineSocket.values();
            if (onlineNames.size() > 0){
                for (String name : onlineNames) {
                    rs.append(name + Constants.SPLIT);
                }
                /*去掉最后一个分隔符*/
                msg = rs.substring(0,rs.lastIndexOf(Constants.SPLIT));
                sendMsgToAll(flag,msg);
            }
        }else if (flag == 2 || flag == 3){
            //读到消息 群发 或者@消息
            String newMsg = dis.readUTF();//消息
            //得到发件人
            String sendName = ServerChat.onlineSocket.get(socket);
            //如 小张 时间 内容
            StringBuilder msgFinal = new StringBuilder();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE");
            if (flag == 2){
                msgFinal.append(sendName).append(" ").append(sdf.format(System.currentTimeMillis()*2)).append("\r\n");
                msgFinal.append("  ").append(newMsg).append("\r\n");
                sendMsgToAll(flag,msgFinal.toString());
            }else {
                String destName = dis.readUTF();
                msgFinal.append(sendName).append(" ").append(sdf.format(System.currentTimeMillis()*2)).append("@").append(destName);
                msgFinal.append("  ").append(newMsg).append("\r\n");
                sendMsgToOne(destName,msgFinal.toString());
            }
        }
    }

    private void sendMsgToOne(String destName, String msg) throws IOException {
        for (Socket sk : ServerChat.onlineSocket.keySet()) {
            if (ServerChat.onlineSocket.get(sk).trim().equals(destName)){
                DataOutputStream dos = new DataOutputStream(sk.getOutputStream());
                dos.writeInt(2);
                dos.writeUTF(msg);
                dos.flush();
            }
        }

    }

    private void sendMsgToAll(int flag, String msg) throws IOException {
        Set<Socket> allOnlineSockets = ServerChat.onlineSocket.keySet();
        for (Socket socket : allOnlineSockets) {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(flag);
            dos.writeUTF(msg);
            dos.flush();
        }
    }
}
