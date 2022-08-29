package com.mengtu.designpattern.pattern.factoty.simple;


import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

public class SimpleCoffeeFactory {

    //1.定义容器对象存储咖啡对象
    private static HashMap<String,Coffee> map = new HashMap<>();
    //2.加载配置文件，只需要加载一次
    static {
        Properties properties = new Properties();
        InputStream inputStream = SimpleCoffeeFactory.class.getClassLoader().getResourceAsStream("bean.properties");
        try {
            properties.load(inputStream);
            Set<Object> set = properties.keySet();
            for (Object o : set) {
               String key = (String) o;
                String className = properties.getProperty(key);
                //通过反射创建对象
                Class<?> clazz = Class.forName(className);
                Coffee coffee = (Coffee) clazz.newInstance();
                map.put(key,coffee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Coffee createCoffee(String name){

        return map.get(name);
    }
}
