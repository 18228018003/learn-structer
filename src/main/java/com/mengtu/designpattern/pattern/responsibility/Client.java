package com.mengtu.designpattern.pattern.responsibility;

public class Client {
    public static void main(String[] args) {
        //创建一个请假条对象
        LeaveRequest request = new LeaveRequest("小明",1,"身体不舒服");
        //创建各级领导对象
        GroupLeader leader = new GroupLeader();
        Manager manager = new Manager();
        GeneralManager generalManager = new GeneralManager();
        leader.setNextHandler(manager);
        manager.setNextHandler(generalManager);

        //提交请假申请
        leader.submit(request);

    }
}
