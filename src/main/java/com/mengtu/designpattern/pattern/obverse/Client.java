package com.mengtu.designpattern.pattern.obverse;

public class Client {
    public static void main(String[] args) {
        //创建公众号对象
        SubscriptionSubject subject = new SubscriptionSubject();
        //订阅公众号
        subject.attach(new WeiXinUser("孙悟空"));
        subject.attach(new WeiXinUser("猪无能"));
        subject.attach(new WeiXinUser("沙悟净"));
        //公众号更新 发出消息给订阅者
        subject.notify("出发去取经了");

    }
}
