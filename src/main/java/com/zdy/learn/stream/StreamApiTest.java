package com.zdy.learn.stream;

import org.junit.Test;

import java.util.stream.Stream;

/**
 *  StreamApi学习
 * @author 周德永
 * @date 2021/11/7 17:14
 */
public class StreamApiTest {
    @Test
    public void test(){
        /*创建无限流*/
        /*遍历前十个偶数*/
        Stream.iterate(0,t -> t + 2).limit(10).forEach(System.out::println);

        Stream.generate(Math::random).limit(10).forEach(System.out::println);

    }
}
