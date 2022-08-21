package com.zdy.learn.lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *  方法引用
 * @author 周德永
 * @date 2021/11/7 16:28
 */
public class MethodRefTest {
    /**
     * 情况一： 对象 :: 实例方法
     * Consumer中的void accept(T t)
     * PrintStream中的void println(T t);
     */
    @Test
    public void test(){
        Consumer<String> consumer = str -> System.out.println(str);
        consumer.accept("北京");

        System.out.println("*******************");

        Consumer<String> con =System.out::println;
        con.accept("成都");
    }


    @Test
    public void test1(){
        Student stu = new Student("zhangsan",18,"男",5600);
        Supplier<String> sup = () -> stu.getName();
        System.out.println(sup.get());
        /*方法引用*/
        System.out.println("*******************");
        Supplier<String> supplier = stu::getName;
        System.out.println(supplier.get());
    }

    /*情况二 类::静态方法*/
    //Comparator中的int compare(T t1,T t2)
    //Integer中的int compare(T t1,T t2)
    @Test
    public void test2(){
        Comparator<Integer> cmp = (t1,t2)->Integer.compare(t1,t2);
        System.out.println(cmp.compare(12, 21));
        /*方法引用*/
        Comparator<Integer> cmp1 = Integer::compare;
        System.out.println(cmp.compare(111, 21));
    }
    @Test
    public void test3(){
        Function<Double,Long> func1 = new Function<Double, Long>() {
            @Override
            public Long apply(Double d) {
                return Math.round(d);
            }
        };
        System.out.println(func1.apply(5.8));
        System.out.println("************************");

        Function<Double,Long> func = d -> Math.round(d);
        System.out.println(func1.apply(50.1));
        System.out.println("************************");
        Function<Double,Long> function = Math::round;
        System.out.println(function.apply(10.21));
    }

    /*情况三 类::实例方法*/
    /*Comparator中的 int compareTo(T t1,T t2)*/
    /*String 中的 int compareTo(T t1,T t2)*/
    @Test
    public void test4(){
        Comparator<String> com1 = (s1,s2) -> s1.compareTo(s2);
        System.out.println(com1.compare("abc","abc"));
        System.out.println("************************");

        Comparator<String> com2 = String::compareTo;
        System.out.println(com1.compare("abe","abc"));
    }
    @Test
    public void test5(){

        BiPredicate<String,String> pre = (s1, s2)->s1.equals(s2);
        System.out.println(pre.test("abc", "abc"));
        System.out.println("************************");

        BiPredicate<String,String> pre1 = String::equals;
        System.out.println(pre1.test("abc","abe"));
    }
}
