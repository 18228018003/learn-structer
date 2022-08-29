package com.mengtu.designpattern.pattern.responsibility;

/**
 * 部门经理 具体处理者
 */
public class Manager extends Handler{
    public Manager() {
        super(Handler.NUM_ONE,Handler.NUM_THREE);
    }

    @Override
    protected void handleLeave(LeaveRequest leave) {
        System.out.println(leave.getName() + "请假" + leave.getNum() + "天" + leave.getContent() + ".");
        System.out.println("部门经理审批同意");
    }
}
