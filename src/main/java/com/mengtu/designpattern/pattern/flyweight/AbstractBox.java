package com.mengtu.designpattern.pattern.flyweight;

public abstract class AbstractBox {
    //获取图形方法
    public abstract String getShape();

    //显示图形及颜色
    public void display(String color){
        System.out.println("方块形状："+getShape()+"，颜色："+color);
    }
}
