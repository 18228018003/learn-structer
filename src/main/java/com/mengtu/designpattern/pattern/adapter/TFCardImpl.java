package com.mengtu.designpattern.pattern.adapter;

//适配者实现
public class TFCardImpl implements TFCard{

    @Override
    public String readTF() {
        return " read some important things from TFCard";
    }

    @Override
    public void writeTF(String msg) {
        System.out.println("TFCard write msg: "+ msg);
    }

}
