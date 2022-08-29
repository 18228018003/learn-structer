package com.mengtu.designpattern.pattern.command;

public class Client {
    public static void main(String[] args) {
        //创建订单对象
        Order order1 = new Order();
        order1.setDiningTable(1);
        order1.setFoodDir("西红柿鸡蛋面",1);
        order1.setFoodDir("可乐",2);
        //创建订单对象
        Order order2 = new Order();
        order2.setDiningTable(2);
        order2.setFoodDir("排骨炖肉",1);
        order2.setFoodDir("百事可乐",1);

        SeniorChef receive = new SeniorChef();
        OrderCommand cmd1 = new OrderCommand(receive,order1);
        OrderCommand cmd2 = new OrderCommand(receive,order2);
        //创建调用者
        Waiter invoke = new Waiter();
        invoke.setCommand(cmd1);
        invoke.setCommand(cmd2);
        //服务员发起命令
        invoke.orderUp();
    }
}
