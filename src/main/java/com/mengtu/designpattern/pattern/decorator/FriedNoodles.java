package com.mengtu.designpattern.pattern.decorator;

/**
 * 具体构建角色
 */
public class FriedNoodles extends FastFood{

    public FriedNoodles(){
        super(8,"炒面");
    }
    @Override
    public float cost() {
        return getPrice();
    }

}
