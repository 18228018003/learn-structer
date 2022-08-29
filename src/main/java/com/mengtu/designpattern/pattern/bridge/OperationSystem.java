package com.mengtu.designpattern.pattern.bridge;

/**
 * 抽象化角色
 */
public abstract class OperationSystem {
    //声明VideoFile变量
    protected VideoFile videoFile;

    public OperationSystem(VideoFile videoFile){
        this.videoFile = videoFile;
    }

    public abstract void play(String filename);
}
