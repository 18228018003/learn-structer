package com.mengtu.designpattern.pattern.iterator;

public interface StudentAggregate {
    //添加学生功能
    void addStudent(Student stu);

    //删除学生功能
    void removeStudent(Student stu);

    //获取迭代器功能对象
    StudentIterator getStudentIterator();

}
