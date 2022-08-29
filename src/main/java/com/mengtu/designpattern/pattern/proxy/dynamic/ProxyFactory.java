package com.mengtu.designpattern.pattern.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
    //声明目标对象
    private TrainStation station = new TrainStation();

    public SellTickets getProxyObject(){
        //返回代理对象
        SellTickets o = (SellTickets) Proxy.newProxyInstance(
                station.getClass().getClassLoader(),
                station.getClass().getInterfaces(),
                new InvocationHandler() {
                    //Object proxy:代理对象
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("invoke 方法执行了 ");
                        Object invoke = method.invoke(station, args);
                        return invoke;
                    }
                }
        );
        return o;
    }
}
