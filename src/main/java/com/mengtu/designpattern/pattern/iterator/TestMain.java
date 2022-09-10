package com.mengtu.designpattern.pattern.iterator;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestMain {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

    }
    public Map.Entry<java.lang.String, java.lang.String> getTailByReflection(Map<String, String> map)
            throws NoSuchFieldException, IllegalAccessException {
        Field tail = map.getClass().getDeclaredField("tail");
        tail.setAccessible(true);
        return (Map.Entry<String, String>) tail.get(map);
    }
}
