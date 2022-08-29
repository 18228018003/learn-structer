package com.mengtu.designpattern.pattern.strategy;

public class Client {
    public static void main(String[] args) {
        //春节期间 使用春节促销活动
        SalesMan salesMan = new SalesMan(new StrategyA());
        salesMan.salesManShow();
    }
}
