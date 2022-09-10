package com.mengtu.designpattern.pattern.iterator;

public class Client {
    public static void main(String[] args) {
        StudentAggregate aggregate = new StudentAggregateImpl();
        aggregate.addStudent(new Student("张三","0001"));
        aggregate.addStudent(new Student("李四","0002"));
        aggregate.addStudent(new Student("王五","0003"));
        aggregate.addStudent(new Student("赵六","0004"));

        StudentIterator iterator = aggregate.getStudentIterator();
        while (iterator.hasNext()) {
            Student next = iterator.next();
            System.out.println(next);
        }
    }
}
