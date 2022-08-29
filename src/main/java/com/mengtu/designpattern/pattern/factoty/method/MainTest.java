package com.mengtu.designpattern.pattern.factoty.method;

public class MainTest {
    public static void main(String[] args) {
        CoffeeStore store = new CoffeeStore();
        CoffeeFactory factory = new AmericanCoffeeFactory();
        store.setFactory(factory);
        Coffee coffee = store.orderCoffee();
        System.out.println(coffee.getName());
    }
}
