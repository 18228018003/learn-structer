package com.mengtu.designpattern.pattern.single;

import java.io.Serializable;

public class Singleton implements Serializable {

    private volatile static Singleton singleton;
    private  static boolean flag = false;
    private Object obj;

    private Singleton(){
        synchronized (Singleton.class){
            if (flag){
                throw new RuntimeException("单例模式");
            }
            flag = true;
        }
    }

    public static Singleton getInstance(){
        if (singleton == null){
            synchronized (Singleton.class){
                if (singleton == null){
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
    public Object readResolve(){
        return Singleton.singleton;
    }
}
