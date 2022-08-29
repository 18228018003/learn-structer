package com.mengtu.designpattern.pattern.proxy.static_proxy;

/**
 * 代售点  代理类
 */
public class ProxyPoint implements SellTickets{
    private TrainStation station = new TrainStation();

    @Override
    public void sell() {
        System.out.println("代售点收取一些服务费用");
        station.sell();
    }
}
