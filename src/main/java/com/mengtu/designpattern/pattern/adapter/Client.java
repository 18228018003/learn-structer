package com.mengtu.designpattern.pattern.adapter;

public class Client {
    public static void main(String[] args) {
        Computer computer = new Computer();
        String s = computer.readSD(new SDCardImpl());
        System.out.println(s);

        String sd = computer.readSD(new SDAdapterTF(new TFCardImpl()));
        System.out.println(sd);
    }
}
