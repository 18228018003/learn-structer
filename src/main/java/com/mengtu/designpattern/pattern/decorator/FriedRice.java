package com.mengtu.designpattern.pattern.decorator;

/**
 * (具体构建角色）
 */
public class FriedRice extends FastFood{

    public FriedRice(){
        super(10,"炒饭");
    }

    @Override
    public float cost() {
        return getPrice();
    }
}
