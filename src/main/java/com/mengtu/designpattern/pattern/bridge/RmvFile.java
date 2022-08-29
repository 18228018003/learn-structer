package com.mengtu.designpattern.pattern.bridge;

/**
 * rmv视频文件(具体的实现化角色）
 */
public class RmvFile implements VideoFile{

    @Override
    public void decode(String filename) {
        System.out.println("rmv 视频文件 ：" + filename);
    }
}
