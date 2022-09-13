package com.mengtu.net.bio.chat1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientChat implements ActionListener {
    //设计界面
    private JFrame win = new JFrame();
    //消息内容框架
    public JTextArea smsContent = new JTextArea(23,50);
    //发送消息的框
    private JTextArea smsSend = new JTextArea(4,40);

    public JList<String> onlineUsers = new JList<>();

    /*是否私聊按钮*/
    private JCheckBox isPrivateBn = new JCheckBox("私聊");
    /*消息按钮*/
    private JButton sendBn = new JButton("发送");
    /*登录界面*/
    private JFrame loginView;
    private JTextField ipEt,nameEt,idEt;
    private Socket socket;

    public static void main(String[] args) {
        ClientChat chat = new ClientChat();
        chat.initView();
    }

    private void initView() {
        /*初始化聊天窗口界面*/
        win.setSize(650,600);
        /*展示登录界面*/
        displayLoginView();
        /*展示聊天界面*/
//        displayChatView();
    }

    private void displayChatView() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        win.add(bottomPanel,BorderLayout.SOUTH);
        bottomPanel.add(smsSend);
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btns.add(sendBn);
        btns.add(isPrivateBn);
        bottomPanel.add(btns,BorderLayout.EAST);
        smsContent.setBackground(new Color(0xdd,0xdd,0xdd));
        win.add(new JScrollPane(smsContent),BorderLayout.CENTER);
        smsContent.setEditable(false);
        Box rightBox = new Box(BoxLayout.Y_AXIS);
        onlineUsers.setFixedCellWidth(120);
        onlineUsers.setVisibleRowCount(13);
        rightBox.add(new JScrollPane(onlineUsers));
        win.add(rightBox,BorderLayout.EAST);
        win.pack();
        setWindowCenter(win,650,600,true);
        sendBn.addActionListener(this);
    }

    private void displayLoginView() {
        /*先让用户进行登录
        * 服务端ip 用户名 id
        */
        loginView = new JFrame("登录");
        loginView.setLayout(new GridLayout(3,1));
        loginView.setSize(400,600);

        JPanel ip = new JPanel();
        JLabel label = new JLabel("  IP:");
        ip.add(label);
        ipEt = new JTextField(20);
        ip.add(ipEt);
        loginView.add(ip);

        JPanel name = new JPanel();
        JLabel label1 = new JLabel("姓名:");
        name.add(label1);
        nameEt = new JTextField(20);
        name.add(nameEt);
        loginView.add(name);

        JPanel btnView = new JPanel();
        JButton login = new JButton("登录");
        btnView.add(login);
        JButton cancel = new JButton("取消");
        btnView.add(cancel);
        loginView.add(btnView);
        loginView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setWindowCenter(loginView,400,260,true);

        /*给登录和取消绑定点击事件*/
        login.addActionListener(this);
        cancel.addActionListener(this);
    }

    private void setWindowCenter(JFrame frame, int w, int h, boolean flag) {
        Dimension ds = frame.getToolkit().getScreenSize();

        int width = ds.width;
        int height = ds.height;
        System.out.println(width+"*"+height);
        frame.setLocation(width/2-w/2,height/2-h/2);
        frame.setVisible(flag);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        switch (btn.getText()){
            case "登录":
                String ip = ipEt.getText();
                String name = nameEt.getText();
                String msg = "";
                if (!ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
                    msg = "请输入合法ip";
                }else if (name.matches("\\s{1,}")){
                    msg = "必须一个字以上";
                }

                if (!msg.equals("")){
                    JOptionPane.showMessageDialog(loginView,msg);
                }else {
                    try {
                        win.setTitle(name);
                        socket = new Socket(ip,Constants.PORT);
                        new ClientReader(this,socket).start();

                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        dos.writeInt(1);
                        dos.writeUTF(name.trim());
                        dos.flush();
                        loginView.dispose();
                        displayChatView();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
        }
    }
}
