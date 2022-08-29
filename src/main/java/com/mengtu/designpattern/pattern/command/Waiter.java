package com.mengtu.designpattern.pattern.command;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务员类 属于请求者角色
 */
public class Waiter {
    //持有多个命令对象
    private List<Command> commands = new ArrayList<>();

    public void setCommand(Command cmd){
        //将cmd对象存储到集合中
        if (cmd == null) return;
        commands.add(cmd);
    }

    //发起命令的功能
    public void orderUp(){
        System.out.println("服务员说：有新订单了");
        for (Command command : commands) {
            command.execute();
        }
    }
}
