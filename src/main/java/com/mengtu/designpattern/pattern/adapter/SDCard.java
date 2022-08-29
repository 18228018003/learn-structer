package com.mengtu.designpattern.pattern.adapter;

//目标接口
public interface SDCard {
    //从SD卡读数据
    String readSD();
    //忘SD卡写数据
    void writeSD(String msg);
}
