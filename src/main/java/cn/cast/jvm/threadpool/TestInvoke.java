package cn.cast.jvm.threadpool;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestInvoke {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        testInvokeAll();
        testInvokeAny();
    }

    private static void testInvokeAny() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        String any = pool.invokeAny(Arrays.asList(
                () -> {
                    System.out.println("begin 1");
                    Thread.sleep(1000);
                    return "1";
                },
                () -> {
                    System.out.println("begin 2");
                    Thread.sleep(500);
                    return "2";
                },
                () -> {
                    System.out.println("begin 3");
                    Thread.sleep(2000);
                    return "3";
                }
        ));
        pool.shutdown();
        System.out.println(any);
    }

    private static void testInvokeAll() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        List<Future<String>> futureList = pool.invokeAll(Arrays.asList(
                () -> {
                    System.out.println("begin 1");
                    Thread.sleep(1000);
                    return "1";
                },
                () -> {
                    System.out.println("begin 2");
                    Thread.sleep(500);
                    return "2";
                },
                () -> {
                    System.out.println("begin 3");
                    Thread.sleep(2000);
                    return "3";
                }
        ));
        futureList.forEach(stringFuture -> {
            try {
                System.out.println(stringFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }
}
