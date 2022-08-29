package com.mengtu.designpattern.pattern.template;

/**
 * 抽象类
 */
public abstract class AbstractClass {

    //模板方法定义
    public final void cookProcess(){
        pourOil();
        heatOil();
        pourVegetable();
        pourSauce();
        fry();
    }

    public void pourOil(){
        System.out.println("倒油");
    }
    public void heatOil(){
        System.out.println("热油");
    }
    //往锅里倒菜
    public abstract void pourVegetable();

    //放调料
    public abstract void pourSauce();

    public void fry(){
        System.out.println("炒菜");
    }


}
