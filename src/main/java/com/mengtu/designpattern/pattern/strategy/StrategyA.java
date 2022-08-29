package com.mengtu.designpattern.pattern.strategy;

/**
 * 具体策略类
 */
public class StrategyA implements Strategy{
    @Override
    public void show() {
        System.out.println("满300减200");
    }
}
