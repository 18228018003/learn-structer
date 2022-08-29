package com.mengtu.designpattern.pattern.facade;

//外观类
public class SmartAppliancesFacade {

    //聚合电灯对象，空调对象，电视机对象
    private Light light;
    private TV tv;
    private AirCondition airCondition;

    public SmartAppliancesFacade(){
        light = new Light();
        tv = new TV();
        airCondition = new AirCondition();
    }

    public void say(String message){
        if (message.equals("打开")){
            on();
        }else if (message.equals("关闭")){
            off();
        }else {
            System.out.println("我还听不懂你说的话！！！");
        }
    }

    private void off() {
        tv.off();
        light.off();
        airCondition.off();
    }

    private void on() {
        tv.on();
        light.on();
        airCondition.on();
    }
}
