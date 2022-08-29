package com.mengtu.designpattern.pattern.proto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Client {
    public static void main(String[] args) throws Exception {
        Citation c = new Citation();
        Student student = new Student();
        student.setName("张三");
        c.setStudent(student);

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\yaya\\file\\a.txt"));
        oos.writeObject(c);
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:\\yaya\\file\\a.txt"));
        Citation o = (Citation) ois.readObject();
        ois.close();

        Student stu = o.getStudent();
        stu.setName("李四");
        c.show();
        o.show();
//        Citation clone = c.clone();
//        clone.getStudent().setName("李四");
//        c.show();
//        clone.show();
    }
}
