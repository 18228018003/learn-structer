package com.mengtu.designpattern.pattern.flyweight;

import cn.cast.list.map.map.HashMap;

public class BoxFactory {
    private HashMap<String,AbstractBox> map;

    //在构造方法中进行初始化操作
    private BoxFactory(){
        map = new HashMap<>();
        map.put("I",new IBox());
        map.put("L",new LBox());
        map.put("O",new OBox());
    }

    //根据名称获取图形对象
    public AbstractBox getShape(String name){
        return map.get(name);
    }

    private static BoxFactory factory = new BoxFactory();

    public static BoxFactory getInstance(){
        return factory;
    }
}
