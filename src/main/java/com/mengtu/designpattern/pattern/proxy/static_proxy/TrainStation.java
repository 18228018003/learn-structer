package com.mengtu.designpattern.pattern.proxy.static_proxy;

//具体主题类
public class TrainStation implements SellTickets{
    @Override
    public void sell() {
        System.out.println("火车站买票");
    }

}
