package com.mengtu.designpattern.pattern.adapter;

public class SDCardImpl implements SDCard{

    @Override
    public String readSD() {
        return " read some important things from SDCard";
    }

    @Override
    public void writeSD(String msg) {
        System.out.println("SDCard write msg: "+ msg);
    }
}
