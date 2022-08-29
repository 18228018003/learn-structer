package com.mengtu.designpattern.pattern.build;

public class Client {
    public static void main(String[] args) {
        Builder builder = new MobileBuilder();
        Director director = new Director(builder);
        Bike bike = director.construct();
        System.out.println(bike);
    }
}
