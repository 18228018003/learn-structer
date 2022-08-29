package com.mengtu.designpattern.pattern.decorator;

/**
 * 鸡蛋类 具体装饰者
 */
public class Egg extends Garnish{


    public Egg(FastFood fastFood) {
        super(fastFood,2,"加鸡蛋");
    }

    @Override
    public float cost() {
        //计算加个
        return getPrice() + getFastFood().cost();
    }

    @Override
    public String getDesc() {
        return super.getDesc() + getFastFood().getDesc();
    }
}
