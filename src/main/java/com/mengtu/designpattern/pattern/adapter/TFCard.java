package com.mengtu.designpattern.pattern.adapter;

/**
 * 适配者类的方法
 */
public interface TFCard {
    //从TF卡读数据
    String readTF();
    //忘TF卡写数据
    void writeTF(String msg);
}
