package com.mengtu.netty.rpc.service;

public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHello(String msg) {
//        int i = 1/0;
        return "hello, "+msg;
    }
}
