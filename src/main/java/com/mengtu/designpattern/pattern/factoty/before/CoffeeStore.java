package com.mengtu.designpattern.pattern.factoty.before;

public class CoffeeStore {
    public Coffee orderCoffee(String type){
        //声明coffee类型变量 下面违背了开闭原则
        Coffee coffee;
        if ("American".equals(type)){
            coffee = new AmericanCoffee();
        }else if ("Latte".equals(type)){
            coffee = new LatteCoffee();
        }else {
            throw new RuntimeException("type is not match");
        }
        coffee.addMilk();
        coffee.addSugar();
        return coffee;
    }
}
