package com.mengtu.designpattern.principle.openclosed;

public class Client {
    public static void main(String[] args) {
        //1.创建输入法对象
        SouGouInput input = new SouGouInput();
        //2.创建皮肤对象
        DefaultSkin skin = new DefaultSkin();
        MengtuSkin mengtuSkin = new MengtuSkin();
        //3.将皮肤设置到输入法中
        input.setSkin(mengtuSkin);
        //4.显示皮肤
        input.disPlay();
    }
}
