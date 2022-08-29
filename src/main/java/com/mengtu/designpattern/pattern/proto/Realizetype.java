package com.mengtu.designpattern.pattern.proto;

public class Realizetype implements Cloneable{
    public Realizetype() {
        System.out.println("原型对象创建成功");
    }

    @Override
    protected Realizetype clone() throws CloneNotSupportedException {
        System.out.println("具体原型赋值成功");
        return (Realizetype) super.clone();
    }
}
