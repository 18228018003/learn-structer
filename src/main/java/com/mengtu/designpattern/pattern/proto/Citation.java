package com.mengtu.designpattern.pattern.proto;

import java.io.Serializable;

public class Citation implements Cloneable, Serializable {

//    private String name;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    protected Citation clone() throws CloneNotSupportedException {
        return (Citation) super.clone();
    }

    public void show(){
        System.out.println(student.getName() + "同学：在XXXXXXXXXXXXXXXXXX表现良好，特发此状！！");
    }

}
