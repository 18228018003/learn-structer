package com.mengtu.structer.completablefuture;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;


public class Demo {


    private static void demo1() {
        SmallTools.printTimeAndThread("小白进入餐厅");
        SmallTools.printTimeAndThread("小白点了 番茄炒蛋 + 一碗米饭");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            SmallTools.printTimeAndThread("厨师炒菜");
            SmallTools.sleepMills(200);
            SmallTools.printTimeAndThread("打饭");
            SmallTools.sleepMills(200);
            return "番茄炒蛋+ 米饭好了";
        });
        SmallTools.printTimeAndThread("小白在打王者");
        SmallTools.printTimeAndThread(String.format("%s,小白开吃",future.join()));
    }

    private static void demo2() {
        SmallTools.printTimeAndThread("小白进入餐厅");
        SmallTools.printTimeAndThread("小白点了 番茄炒蛋 + 一碗米饭");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            SmallTools.printTimeAndThread("厨师炒菜");
            SmallTools.sleepMills(200);
            return "番茄炒蛋";
        }).thenComposeAsync(dish-> CompletableFuture.supplyAsync(()->{
            SmallTools.printTimeAndThread("服务员打饭");
            SmallTools.sleepMills(200);
            return dish + "米饭";
        }));

        SmallTools.printTimeAndThread("小白在打王者");
        SmallTools.printTimeAndThread(String.format("%s,小白开吃",future.join()));
    }
    private static void demo3(){
        SmallTools.printTimeAndThread("小白进入餐厅");
        SmallTools.printTimeAndThread("小白点了 番茄炒蛋 + 一碗米饭");
        CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
            SmallTools.printTimeAndThread("厨师炒菜");
            SmallTools.sleepMills(200);
            return "番茄炒蛋";
        }).thenCombineAsync(CompletableFuture.supplyAsync(() -> {
            SmallTools.printTimeAndThread("服务员煮饭");
            SmallTools.sleepMills(300);
            return "米饭";
        }),(dish,rice)->{
            SmallTools.printTimeAndThread("服务员打饭");
            SmallTools.sleepMills(100);
            return String.format("%s + %s 好了",dish,rice);
        });
        SmallTools.printTimeAndThread("小白在打王者");
        SmallTools.printTimeAndThread(String.format("%s,小白开吃",future.join()));
    }

    private static void demo4(){
        SmallTools.printTimeAndThread("小白吃好了");
        SmallTools.printTimeAndThread("小白 结账、要求开发票");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            SmallTools.printTimeAndThread("服务员收款 500元");
            SmallTools.sleepMills(100);
            return "500";
        }).thenApplyAsync(money -> {
            SmallTools.printTimeAndThread(String.format("服务员开发票 面额%s 元", money));
            SmallTools.sleepMills(200);
            return String.format("%s元发票", money);
        });

        SmallTools.printTimeAndThread("小白 接到朋友电话 想一起打游戏");
        SmallTools.printTimeAndThread(String.format("小白拿到%s，回家",future.join()));
    }
    private static void demo5(){
        SmallTools.printTimeAndThread("张三走出餐厅，来到公交站");
        SmallTools.printTimeAndThread("等待700路公交或者800路公交 公交到了");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            SmallTools.printTimeAndThread("700路公交正在路上");
            SmallTools.sleepMills(200);
            return "700";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            SmallTools.printTimeAndThread("800路公交正在路上");
            SmallTools.sleepMills(300);
            return "800";
        }), firstBusCome -> {
            if (firstBusCome.startsWith("700")){
                throw new RuntimeException("撞树上");
            }
            return firstBusCome;
        }).exceptionally(e -> {
            SmallTools.printTimeAndThread(e.getMessage());
            SmallTools.printTimeAndThread("小白坐出租车回家");
            return "出租车,到了";
        });

        SmallTools.printTimeAndThread(String.format("小白坐车%s，回家",future.join()));
    }
    public static void main1(String[] args) {
//        demo5();
        /*
            supplyAsync开启异步任务 有返回值          thenApply做任务的后置处理 有返回值
            thenCompose 连接两个异步任务       applyToEither获取最先完成的异步任务
            thenCombine 合并两个异步任务       exceptionally用来处理异常
            runAsync 开启异步任务 无返回值            thenAccept做任务的后置处理 无返回值
            thenRun 不接受前面异步线程参数，也没有返回值
         */
        /*
            thenCombine、thenAcceptBoth、runAfterBoth
            区别：thenAcceptBoth有两个参数 无返回值
            runAfterBoth不关心前两个任务的结果 也无返回值
         */

        /*
        * applyToEither、acceptEither、runAfterEither区别
        * acceptEither      接收最快返回结果的异步任务，无返回值
        * runAfterEither    既不关心执行最快任务的结果又无返回值
        * */

        /*
        * exceptionally、handle、whenComplete
        * handle（BiFuction）前面任务正常执行，收到正常执行的结果，出现异常，就接收到异常，会返回结果
        * whenComplete 和 handle类似 无返回结果
        * */



    }
    private static void demo6(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,
                5, 1,
                TimeUnit.HOURS,
                new ArrayBlockingQueue<>(100));
//        Executors.newSingleThreadExecutor();
//        Executors.newCachedThreadPool();
        executor.execute(()->{
            System.out.println("4+4");

        });
        System.out.println("asas");
    }
    private static void demo7(){
        SmallTools.printTimeAndThread("小白和小伙伴们 进餐厅点菜");
        long startTime = System.currentTimeMillis();
        ArrayList<Dish> dishes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Dish dish = new Dish("菜"+i,1);
            dishes.add(dish);
        }
        for (Dish dish : dishes) {
            CompletableFuture.runAsync(dish::make).join();
        }
        SmallTools.printTimeAndThread("菜做好了，上桌 耗时" + (System.currentTimeMillis() - startTime)/1000 + "s");
    }
    private static void demo8(){
        SmallTools.printTimeAndThread("小白和小伙伴们 进餐厅点菜");
        long startTime = System.currentTimeMillis();
        ArrayList<Dish> dishes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Dish dish = new Dish("菜"+i,1);
            dishes.add(dish);
        }
        ArrayList<CompletableFuture<Void>> cfList = new ArrayList<>();
        for (Dish dish : dishes) {
            CompletableFuture<Void> cf = CompletableFuture.runAsync(dish::make);
            cfList.add(cf);
        }
        CompletableFuture.allOf(cfList.toArray(new CompletableFuture[cfList.size()])).join();
        SmallTools.printTimeAndThread("菜做好了，上桌 耗时" + (System.currentTimeMillis() - startTime)/1000 + "s");
    }
    private static void demo9(){
        SmallTools.printTimeAndThread("小白和小伙伴们 进餐厅点菜");
        long startTime = System.currentTimeMillis();
        CompletableFuture[] futures = IntStream.rangeClosed(1, 10).mapToObj(i -> new Dish("菜" + i, 1))
                .map(dish -> CompletableFuture.runAsync(dish::make))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
        SmallTools.printTimeAndThread("菜做好了，上桌 耗时" + (System.currentTimeMillis() - startTime)/1000 + "s");
    }
    public static void main(String[] args) {

        Thread thread = new Thread(()->{
            Thread currentThread = Thread.currentThread();
            while (true){

                if (currentThread.isInterrupted()){
                    break;
                };
            }
        },"线程1");
        thread.start();

        thread.interrupt();
        System.out.println("主线程");

    }
}
