package com.mengtu.designpattern.pattern.adapter;

//计算机类
public class Computer {
    public String readSD(SDCard sdCard){
        if (sdCard == null){
            throw new RuntimeException("sd card is not null");
        }
        return sdCard.readSD();
    }
}
