package com.mengtu.designpattern.pattern.template;

//炒白菜
public class ConcreteClass_BaiCai extends AbstractClass{

    @Override
    public void pourVegetable() {
        System.out.println("下锅的蔬菜是包菜");
    }

    @Override
    public void pourSauce() {
        System.out.println("下锅的酱料是辣椒");
    }
}
