package com.zdy.learn.tree;

import java.util.Comparator;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/20 21:26
 */
public class Main {

    private static class PersonCompartor implements Comparator<Person>{
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getAge()-o2.getAge();
        }
    }
    private static class PersonCompartor2 implements Comparator<Person>{
        @Override
        public int compare(Person o1, Person o2) {
            return o2.getAge()-o1.getAge();
        }
    }

    public static void main(String[] args) {

    }
}
