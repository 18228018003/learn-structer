package com.mengtu.designpattern.pattern.decorator;

public class Bacon extends Garnish{

    public Bacon(FastFood fastFood) {
        super(fastFood, 2, "培根");
    }

    @Override
    public float cost() {
        return getPrice() + getFastFood().cost();
    }

    @Override
    public String getDesc() {
        return super.getDesc() + getFastFood().getDesc();
    }
}
