package com.mengtu.designpattern.pattern.decorator;

public class Client {
    public static void main(String[] args) {
        FastFood food = new FriedRice();
        System.out.println(food.getDesc() + " " + food.cost() + " 元 ");
        System.out.println( " ============ ");
        //在上面的炒饭中加一个鸡蛋
        food = new Egg(food);
        System.out.println(food.getDesc() + " " + food.cost() + " 元 ");
        System.out.println( " ============ ");
        food = new Egg(food);
        System.out.println(food.getDesc() + " " + food.cost() + " 元 ");
        food = new Bacon(food);
        System.out.println( " ============ ");
        System.out.println(food.getDesc() + " " + food.cost() + " 元 ");

    }
}
