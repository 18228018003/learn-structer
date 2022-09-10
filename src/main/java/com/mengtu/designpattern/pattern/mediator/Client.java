package com.mengtu.designpattern.pattern.mediator;

public class Client {
    public static void main(String[] args) {
        MediatorStructure mediator = new MediatorStructure();
        //创建租房者
        Tenant tenant = new Tenant("张三",mediator);
        HouseOwner houseOwner = new HouseOwner("李四",mediator);

        //中介者必须知道房主和租房者
        mediator.setTenant(tenant);
        mediator.setHouseOwner(houseOwner);

        tenant.contact("我要买别墅！！！！");
        houseOwner.contact("我这有十栋别墅");
    }
}
