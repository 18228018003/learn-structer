package com.mengtu.designpattern.pattern.factoty.simple;


public class CoffeeStore {
    public Coffee orderCoffee(String type){
        //声明coffee类型变量 下面违背了开闭原则
        Coffee coffee = SimpleCoffeeFactory.createCoffee(type);
        coffee.addMilk();
        coffee.addSugar();
        return coffee;
    }
}
