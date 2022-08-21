package cn.cast.jvm.thread;

public class Test8Lock {
    public static void main(String[] args) {
        Number number = new Number();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"begin");
            number.a();
        },"t1").start();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"begin");
            number.b();
        },"t2").start();
    }
}
class Number{
    public synchronized void a(){
        System.out.println("1");
    }
    public synchronized void b(){
        System.out.println("2");
    }
}
