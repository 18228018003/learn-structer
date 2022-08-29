package com.mengtu.designpattern.pattern.bridge;

public class Client {
    public static void main(String[] args) {
        //创建mac对象
        OperationSystem system = new MacOs(new AviFile());
        //使用具体的操作系统播放视频文件
        system.play("战狼3");
    }
}
