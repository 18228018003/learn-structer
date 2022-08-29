package com.mengtu.designpattern.principle.dependencyreverse;

public class XiJieHardDisk implements HardDisk{
    //存储数据的方法
    public void save(String data){
        System.out.println("使用希捷硬盘存储数据"  + data);
    }

    public String get(){
        System.out.println("使用希捷硬盘取数据");
        return "数据";
    }
}
