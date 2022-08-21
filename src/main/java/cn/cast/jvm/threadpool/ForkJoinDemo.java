package cn.cast.jvm.threadpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinDemo {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(4);
        Integer sum = pool.invoke(new MyTask(10));
        //new MyTask(5) 5+MyTask(4)
        System.out.println(sum);
    }
}

//1-n整数之间的和
class MyTask extends RecursiveTask<Integer>{
    private int n;

    public MyTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        /*终止条件*/
        if (n==1){
            return n;
        }
        MyTask t1 = new MyTask(n - 1);
        t1.fork(); //让线程去执行此任务
        return t1.join() + n;
    }
}

