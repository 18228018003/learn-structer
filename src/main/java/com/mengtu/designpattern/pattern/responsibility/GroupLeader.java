package com.mengtu.designpattern.pattern.responsibility;

/**
 * 具体的处理者类 小组长
 */
public class GroupLeader extends Handler{

    public GroupLeader() {
        super(0,Handler.NUM_ONE);
    }


    @Override
    protected void handleLeave(LeaveRequest leave) {
        System.out.println(leave.getName() + "请假" + leave.getNum() + "天" + leave.getContent() + ".");
        System.out.println("小组长审批同意");
    }

}
