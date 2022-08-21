package com.zdy.learn.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/20 21:26
 */
public class Person{
    private int age;

    public Person() {
    }

    public Person(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();
        queue.offer(null);
        queue.offer(null);
        ArrayList<Object> list = new ArrayList<>();
        while (!queue.isEmpty()){
            System.out.println(queue.poll());
        }
    }
}
