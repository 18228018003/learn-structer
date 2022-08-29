package com.mengtu.designpattern.pattern.state.before;

//电梯类
public class Left implements ILeft{
    private int state;
    @Override
    public void setState(int state) {
        this.state = state;
    }

    @Override
    public void open() {
        switch (state){
            case OPENING_STATE:
                break;
            case CLOSING_STATE:
//                执行开启操作
                System.out.println("电梯打开了");
                this.state = OPENING_STATE;
                break;
            case STOPPING_STATE:
                System.out.println("电梯打开了");
                setState(OPENING_STATE);
                break;
            case RUNNING_STATE:
                break;
        }
    }

    @Override
    public void close() {
        switch (state){
            case OPENING_STATE:
                System.out.println("关闭");
                this.state = CLOSING_STATE;
                break;
            case CLOSING_STATE:
                break;
            case STOPPING_STATE:
                break;
            case RUNNING_STATE:
                break;
        }
    }

    @Override
    public void run() {

    }

    @Override
    public void stop() {

    }
}
