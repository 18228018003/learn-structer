package com.mengtu.designpattern.pattern.state.after;

//电影开启状态类

public class OpeningState extends LiftState{

    @Override
    public void open() {
        System.out.println("电梯开启。。。");
    }

    @Override
    public void close() {
        super.context.setLiftState(Context.CLOSING_STATE);
    }

    @Override
    public void run() {

    }

    @Override
    public void stop() {

    }
}
