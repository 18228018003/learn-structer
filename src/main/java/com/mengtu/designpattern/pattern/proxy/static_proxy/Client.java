package com.mengtu.designpattern.pattern.proxy.static_proxy;

public class Client {
    public static void main(String[] args) {
        ProxyPoint point = new ProxyPoint();
        point.sell();
    }
}
