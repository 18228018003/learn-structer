package com.mengtu.designpattern.pattern.responsibility;

/**
 * 部门经理 具体处理者
 */
public class GeneralManager extends Handler{
    public GeneralManager() {
        super(Handler.NUM_THREE,Handler.NUM_SEVEN);
    }

    @Override
    protected void handleLeave(LeaveRequest leave) {
        System.out.println(leave.getName() + "请假" + leave.getNum() + "天" + leave.getContent() + ".");
        System.out.println("总经理审批同意");
    }
}
