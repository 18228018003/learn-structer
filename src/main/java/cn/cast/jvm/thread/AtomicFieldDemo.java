package cn.cast.jvm.thread;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicFieldDemo {
    public static void main(String[] args) {
        Student student = new Student();
        AtomicReferenceFieldUpdater<Student, String> updater = AtomicReferenceFieldUpdater.newUpdater(Student.class,String.class,"name");
        if (updater.compareAndSet(student,null,"张三")) {
            System.out.println(student);
        }

    }
}

class Student{
    volatile String name;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
