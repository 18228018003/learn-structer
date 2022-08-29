package com.mengtu.designpattern.pattern.proto;

import java.io.Serializable;

public class Student implements Serializable {
    //学生的姓名
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
