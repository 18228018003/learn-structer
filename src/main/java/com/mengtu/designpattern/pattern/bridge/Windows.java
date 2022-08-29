package com.mengtu.designpattern.pattern.bridge;

/**
 * 扩展抽象化角色
 */
public class Windows extends OperationSystem{

    public Windows(VideoFile videoFile) {
        super(videoFile);
    }

    @Override
    public void play(String filename) {
        videoFile.decode(filename);
    }
}
