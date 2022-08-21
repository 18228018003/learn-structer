package cn.cast.jvm.aqs;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestWriteReadLock {
    public static void main1(String[] args) throws InterruptedException {
        Container container = new Container();
        Stu stu = new Stu("张三",23,"男");

        new Thread(()->{
            container.write(new Stu("张三",23,"男"));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"write 1").start();

        Thread.sleep(1000);
        new Thread(()->{
            System.out.println(container.read());
        },"t1").start();
        new Thread(()->{
            System.out.println(container.read());
        },"t2").start();
        new Thread(()->{
            System.out.println(container.read());
        },"t3").start();
        new Thread(()->{
            System.out.println(container.read());
        },"t4").start();
    }

    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("a","jklda");
        hashMap.put("b","ewqe");
        hashMap.put("c","fsd");
        hashMap.remove("a");
        hashMap.forEach( (k,v)->{
            System.out.println(k);
            System.out.println(v);
        });
    }
}
class Container{
    private Object date;
    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = rw.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = rw.writeLock();
    public Object read(){
        /*获取读锁*/
        System.out.println(Thread.currentThread().getName()+" 获取读锁");
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+" 读取");
            return date;
        }finally {
            System.out.println(Thread.currentThread().getName()+" 释放读锁");
            readLock.unlock();
        }
    }

    public void write(Object obj){
        /*获取读锁*/
        System.out.println(Thread.currentThread().getName()+" 获取写锁");
        writeLock.lock();
        try {
            date = obj;
            System.out.println(Thread.currentThread().getName()+" 写入");
        }finally {
            System.out.println(Thread.currentThread().getName()+" 释放写锁");
            writeLock.unlock();
        }
    }
}
class Stu{

    private String name;
    private int age;
    private String sex;

    public Stu(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Stu{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}