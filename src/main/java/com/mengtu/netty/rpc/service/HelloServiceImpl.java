package com.mengtu.netty.rpc.service;

public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHello(String msg) {
        return msg;
    }
}
