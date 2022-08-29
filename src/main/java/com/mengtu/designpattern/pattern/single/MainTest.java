package com.mengtu.designpattern.pattern.single;

import org.junit.Test;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MainTest {
    //序列化反序列化破坏单例模式
    //解决办法
    @Test
    public void destroySingleton(){
        Singleton singleton = Singleton.getInstance();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\yaya\\file\\a.txt"));
            oos.writeObject(singleton);
            oos.close();
            readObjectFromFile();
            readObjectFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void reflect() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Singleton> clazz = Singleton.class;
        Constructor<Singleton> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        Singleton s1 = constructor.newInstance();
        Singleton s2 = constructor.newInstance();
        System.out.println(s1 == s2);
    }

    public void readObjectFromFile() throws Exception{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:\\yaya\\file\\a.txt"));
        Singleton o = (Singleton) ois.readObject();
        System.out.println(o);
        ois.close();
    }
}
