package com.mengtu.designpattern.pattern.factoty.simple;

public class MainTest {
    public static void main(String[] args) {
        CoffeeStore coffeeStore = new CoffeeStore();
        String latte = coffeeStore.orderCoffee("latte").getName();
        System.out.println(latte);

    }
}
