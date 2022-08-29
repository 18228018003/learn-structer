package com.mengtu.designpattern.pattern.factoty.method;

/**
 * 美式咖啡工厂对象
 */
public class AmericanCoffeeFactory implements CoffeeFactory{
    @Override
    public Coffee createCoffee() {
        return new AmericanCoffee();
    }
}
