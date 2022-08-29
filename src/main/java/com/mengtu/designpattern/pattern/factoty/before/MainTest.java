package com.mengtu.designpattern.pattern.factoty.before;

public class MainTest {
    public static void main(String[] args) {
        CoffeeStore coffeeStore = new CoffeeStore();
        String latte = coffeeStore.orderCoffee("Latte").getName();
        System.out.println(latte);

    }
}
