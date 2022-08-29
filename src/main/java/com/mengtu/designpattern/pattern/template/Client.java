package com.mengtu.designpattern.pattern.template;

public class Client {
    public static void main(String[] args) {
        ConcreteClass_BaiCai baiCai = new ConcreteClass_BaiCai();
        baiCai.cookProcess();
        ConcreteClass_CaiXin caiXin = new ConcreteClass_CaiXin();
        caiXin.cookProcess();
    }
}
