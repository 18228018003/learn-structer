package com.mengtu.designpattern.pattern.flyweight;

public class Client {
    public static void main(String[] args) {
        BoxFactory factory = BoxFactory.getInstance();
        AbstractBox i = factory.getShape("I");
        i.display("灰色");
        AbstractBox l = factory.getShape("L");
        l.display("蓝色");
        AbstractBox o = factory.getShape("O");
        o.display("红色");
    }
}
