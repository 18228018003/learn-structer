package com.mengtu.designpattern.pattern.build;

public abstract class Builder {
    //声明bike类型的变量
    protected Bike bike = new Bike();

    public abstract void buildFrame();

    public abstract void buildSeat();

    public abstract Bike createBike();


}
