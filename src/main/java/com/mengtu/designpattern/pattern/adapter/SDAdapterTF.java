package com.mengtu.designpattern.pattern.adapter;

public class SDAdapterTF implements SDCard{
    //声明适配者类
    private TFCard card;

    public SDAdapterTF(TFCard card){
        this.card = card;
    }

    @Override
    public String readSD() {
        System.out.println("adapter read tf card");
        return card.readTF();
    }

    @Override
    public void writeSD(String msg) {
        System.out.println("adapter write tf card");
        card.writeTF(msg);
    }

}
