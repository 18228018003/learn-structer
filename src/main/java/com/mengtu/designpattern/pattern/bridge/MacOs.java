package com.mengtu.designpattern.pattern.bridge;

public class MacOs extends OperationSystem{

    public MacOs(VideoFile videoFile) {
        super(videoFile);
    }

    @Override
    public void play(String filename) {
        videoFile.decode(filename);
    }
}
