package cn.cast.jvm.thread;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicABADemo {
    static AtomicReference<String> ref = new AtomicReference<>("A");
    static AtomicStampedReference<String> stampedReference = new AtomicStampedReference<>("A", 0);
    public static void test1() throws InterruptedException {
        System.out.println("main start...");
        // 获取值 A
        // 这个共享变量被它线程修改过？
        String prev = ref.get();
        other();
        TimeUnit.SECONDS.sleep(1);
        // 尝试改为 C
        System.out.println("change A->C " + ref.compareAndSet(prev, "C"));
    }
    static void other(){
        new Thread(()->{
            ref.compareAndSet("A","B");
        },"A").start();
        new Thread(()->{
            ref.compareAndSet("B","A");
        },"B").start();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start...");
        // 获取值 A
        // 这个共享变量被它线程修改过？
        String prev = stampedReference.getReference();
        int stamp = stampedReference.getStamp();
        System.out.println(prev);
        System.out.println("版本 " + stamp);
        otherStamp();
        TimeUnit.SECONDS.sleep(1);
        // 尝试改为 C
        System.out.println("change A->C " + stampedReference.compareAndSet(prev, "C",stamp,stamp+1));
    }
    static void otherStamp(){
        new Thread(()->{
            int stamp = stampedReference.getStamp();
            System.out.println(stamp);
            String reference = stampedReference.getReference();
            System.out.println(reference);
            stampedReference.compareAndSet(reference,"B",stamp,stamp+1);
        },"A").start();
        new Thread(()->{
            int stamp = stampedReference.getStamp();
            System.out.println(stamp);
            String reference = stampedReference.getReference();
            System.out.println(reference);
            stampedReference.compareAndSet(reference,"A",stamp,stamp+1);
        },"B").start();
    }
}
