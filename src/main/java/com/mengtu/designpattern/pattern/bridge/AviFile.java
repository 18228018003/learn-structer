package com.mengtu.designpattern.pattern.bridge;

/**
 * avi视频文件（具体的实现化角色）
 */
public class AviFile implements VideoFile{
    @Override
    public void decode(String filename) {
        System.out.println("avi视频文件：" + filename);
    }

}
