package com.mengtu.net.bio.chat1;

import java.io.DataInputStream;
import java.net.Socket;

public class ClientReader extends Thread{
    private Socket socket;
    private ClientChat client;

    public ClientReader(ClientChat client,Socket socket) {
        this.socket = socket;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            while (true){
                int flag = dis.readInt();
                if (flag == 1){
                    String nameData = dis.readUTF();
                    String[] names = nameData.split(Constants.SPLIT);
                    client.onlineUsers.setListData(names);
                }else if (flag == 2){
                    String msg = dis.readUTF();
                    client.smsContent.append(msg);
                    client.smsContent.setCaretPosition(client.smsContent.getText().length());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
