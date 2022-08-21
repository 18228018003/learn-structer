package com.zdy.learn.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/7 16:06
 */
public class LambdaTest {
    @Test
    public void test1(){
        happyTime(500, new Consumer<Double>() {
            @Override
            public void accept(Double aDouble) {
                System.out.println("学习太累了去天上人间消费"+aDouble);
            }
        });
        System.out.println("**************************************");
        happyTime(500, (money)-> System.out.println("学习太累了去天上人间消费"+money));
    }
    public void happyTime(double money, Consumer<Double> con){
        con.accept(money);
    }


    @Test
    public void test2(){
        List<String> list = Arrays.asList("北京", "南京", "天津", "东京", "西京", "普京");
        List<String> filterString = filterString(list, new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("京");
            }
        });
        System.out.println(filterString);
        System.out.println("*********************************");
        /*Lambda表达式  */
        List<String> stringList = filterString(list, (s) -> s.contains("京"));
        System.out.println(stringList);

    }
    /*根据给定的规则，过滤集合中的字符串。此规则由Predicate的方法决定*/
    public List<String> filterString(List<String> list, Predicate<String> predicate){
        ArrayList<String> filterList = new ArrayList<>();
        for (String s : list) {
            if (predicate.test(s)){
                filterList.add(s);
            }
        }
        return filterList;
    }
}
