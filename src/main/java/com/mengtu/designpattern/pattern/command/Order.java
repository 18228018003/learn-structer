package com.mengtu.designpattern.pattern.command;

import java.util.HashMap;
import java.util.Map;

/**
 * 命令模式
 */
public class Order {
    //餐桌号码
    private int diningTable;

    //所下的菜品以及份数
    private Map<String,Integer> foodDir = new HashMap<>();

    public Map<String, Integer> getFoodDir() {
        return foodDir;
    }

    public void setFoodDir(String name, int num) {
        foodDir.put(name,num);
    }

    public int getDiningTable() {
        return diningTable;
    }

    public void setDiningTable(int diningTable) {
        this.diningTable = diningTable;
    }
}
