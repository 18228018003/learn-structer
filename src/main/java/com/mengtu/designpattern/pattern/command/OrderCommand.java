package com.mengtu.designpattern.pattern.command;

import java.util.Map;

/**
 * 具体的命令类
 */
public class OrderCommand implements Command{
    //持有接收者对象
    private SeniorChef receiver;
    private Order order;

    public OrderCommand(SeniorChef chef, Order order) {
        this.receiver = chef;
        this.order = order;
    }


    @Override
    public void execute() {
        System.out.println(order.getDiningTable() + "桌的订单：");
        Map<String, Integer> dir = order.getFoodDir();
        for (String foodName : dir.keySet()) {
            receiver.makeFood(foodName,dir.get(foodName));
        }
        System.out.println(order.getDiningTable() + "桌的餐厅准备完毕");
    }
}
