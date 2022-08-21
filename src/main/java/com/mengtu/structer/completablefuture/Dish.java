package com.mengtu.structer.completablefuture;

import java.util.concurrent.TimeUnit;

public class Dish {
    private String name;
    private Integer time;

    public Dish(String name,Integer time){
        this.time = time;
        this.name = name;
    }

    public void make(){
        SmallTools.sleepMills(TimeUnit.SECONDS.toMillis(this.time));
        SmallTools.printTimeAndThread(this.name + "制作完成,来吃我吧");
    }
}
