package com.mengtu.designpattern.pattern.strategy;

//环境类
public class SalesMan {
    private Strategy strategy;

    public SalesMan(Strategy strategy){
        this.strategy = strategy;
    }

    public void salesManShow(){
        strategy.show();
    }
}
