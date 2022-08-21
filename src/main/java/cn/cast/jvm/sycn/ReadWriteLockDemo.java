package cn.cast.jvm.sycn;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 资源类
 */
class MyCache{
    private final Map<String, Object> map = new HashMap<>();
    /**
     * 创建一个读写锁
     * 它是一个读写融为一体的锁，在使用的时候，需要转换
     */
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    /**
     * 定义写操作
     * 满足：原子 + 独占
     */
    public void put(String key,Object value){
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写入：" + key);
//            try {
//                // 模拟网络拥堵，延迟0.3秒
//                TimeUnit.MILLISECONDS.sleep(300);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            map.put(key,value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成");
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            // 写锁 释放
            rwLock.writeLock().unlock();
        }
    }
    public void get(String key) {
        // 读锁
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在读取:");
//            try {
//                // 模拟网络拥堵，延迟0.3秒
//                TimeUnit.MILLISECONDS.sleep(300);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            Object value = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取完成：" + value);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rwLock.readLock().unlock();
        }
    }
    /**
     * 清空缓存
     */
    public void clean() {
        map.clear();
    }
}
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache cache = new MyCache();
        // 线程操作资源类，5个线程写
        for (int i = 0; i < 5; i++) {
            final int tempInt = i;
            new Thread(()->{
                cache.put(tempInt+"",tempInt+"");
            },String.valueOf(i)).start();
        }
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        // 线程操作资源类， 5个线程读
        for (int i = 0; i < 5; i++) {
            final int tempInt = i;
            new Thread(()->{
                cache.get(tempInt+"");
            }, String.valueOf(i)).start();
        }
    }

}
