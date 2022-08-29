package com.mengtu.designpattern.pattern.proxy.dynamic;

public class Client {
    public static void main(String[] args) {
//        jdkProxy();
        ProxyFactoryCglib factory = new ProxyFactoryCglib();
        TrainStation object = factory.getProxyObject();
        object.sell();
    }

    private static void jdkProxy() {
        ProxyFactory factory = new ProxyFactory();
        SellTickets object = factory.getProxyObject();
        object.sell();
        System.out.println(object.getClass());
        try {
            Thread.sleep(1000000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
