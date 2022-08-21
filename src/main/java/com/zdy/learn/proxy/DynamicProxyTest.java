package com.zdy.learn.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *  动态代理
 * @author 周德永
 * @date 2021/11/7 15:08
 */

interface Human{
    String getBelief();
    void eat(String food);
}

/*被代理类*/
class SuperMan implements Human{
    @Override
    public String getBelief() {
        return "I believe I can fly !";
    }

    @Override
    public void eat(String food) {
        System.out.println("我喜欢吃" + food);
    }
}
/*
* 要想生成动态代理，需要解决的问题
* 问题1，如果根据被加载到内存中的被代理类，动态的创建一个代理类及其对象。
* 问题2，当通过代理类的对象调用方法时，如何动态的去调用被代理类中的同名方法
*/
class ProxyFactory{
    /*调用此方法，返回一个代理类的对象*/
    public static Object getProxyInstance(Object obj){/*obj 被代理类的对象*/
        MyInvocationHandler handler = new MyInvocationHandler();
        handler.bind(obj);
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), handler);
    }
}

public class DynamicProxyTest {
    public static void main(String[] args) {
        Human proxyInstance = (Human) ProxyFactory.getProxyInstance(new SuperMan());
        System.out.println(proxyInstance.getBelief());
        proxyInstance.eat("malat");
    }
}

class MyInvocationHandler implements InvocationHandler {

    private Object obj;/*需要使用被代理类的对象进行赋值*/
    public void bind(Object obj){
        this.obj = obj;
    }

    /*当通过代理类的对象，调用方法a时，就会自动调用如下的方法*/
    /*proxy 代理类对象，method 代理类对象调用的方法*/
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(obj,args);
    }
}