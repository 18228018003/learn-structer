package com.zdy.learn.test;

import org.junit.Test;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/7 12:32
 */
public class Testtoto {
    @Test
    public void test1()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        TemporalAccessor parse = dtf.parse("2019-09-21 17:21:32");
        System.out.println(parse);
    }
    @Test
    public void test21()
    {
        Date date = new Date("2019-10-21");
        System.out.println(date);

    }
}
