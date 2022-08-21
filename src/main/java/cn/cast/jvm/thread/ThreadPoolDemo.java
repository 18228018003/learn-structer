package cn.cast.jvm.thread;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*自定义线程池*/
public class ThreadPoolDemo {

}
@FunctionalInterface
interface RejectPolicy<T>{
    void reject(BlockQueue<T> blockQueue,T task);
}
class ThreadPool{
    /*任务队列*/
    private BlockQueue<Runnable> taskQueue;

    /*线程集合*/
    private final HashSet<Worker> workers = new HashSet<>();

    /*核心线程数*/
    private int coreSize;

    /*超时时间*/
    private long timeout;

    /*时间单位*/
    private TimeUnit timeUnit;

    /*拒绝策略*/
    private RejectPolicy<Runnable> rejectPolicy;

    public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit,int queueCapacity,RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.taskQueue = new BlockQueue<>(queueCapacity);
        this.rejectPolicy = rejectPolicy;
    }

    /*执行任务*/
    public void execute(Runnable task){
        /*当任务数没有超过coreSize时，直接交给worker 对象执行*/
        /*当超过coreSize时,加入任务队列缓存*/
        synchronized (workers){
            if (workers.size() < coreSize){
                Worker worker = new Worker(task);
                worker.start();
                workers.add(worker);
            }else {
                taskQueue.put(task);
                /**
                 * 1) 死等
                 * 2) 带超时等待
                 * 3) 让调用者放弃执行任务
                 * 4) 让调用者抛出异常
                 * 5) 让调用者自己执行任务
                 */
                taskQueue.tryPut(rejectPolicy,task);
            }
        }
    }
    class Worker extends Thread{

        private Runnable task;

        public Worker(Runnable task){
            this.task = task;
        }

        @Override
        public void run() {
            /*执行任务*/
            //1) 当task不为空，执行任务
            //2) 当task执行完毕,在接着从任务队列获取任务
            while (task != null || (task = taskQueue.take()) != null){
                try {
                    task.run();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    task = null;
                }
            }
            synchronized (workers){
                workers.remove(this);
            }
        }
    }
}
class BlockQueue<T>{
    /*1.任务队列*/
    private Deque<T> queue = new ArrayDeque<>();

    /*2.锁*/
    private ReentrantLock lock = new ReentrantLock();

    /*3.生产者条件变量*/
    private Condition fullWaitSet = lock.newCondition();

    /*4.消费者条件变量*/
    private Condition emptyWaitSet = lock.newCondition();

    /*5.容量*/
    private int capacity;

    public BlockQueue(int capacity) {
        this.capacity = capacity;
    }

    /*阻塞获取*/
    public T take(){
        lock.lock();
        try {
            while (queue.isEmpty()){
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }
    /*带超时的阻塞获取*/
    public T poll(long timeout, TimeUnit unit){
        lock.lock();
        try {
            /*将超时时间统一装换为纳秒*/
            long nanos = unit.toNanos(timeout);
            while (queue.isEmpty()){
                try {
                    /*返回的是剩余时间，如果小于等于0*/
                    if (nanos <= 0){
                        return null;
                    }
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }

    /*阻塞添加*/
    public void put(T task){
        lock.lock();
        try {
            while (queue.size() == capacity){
                try {
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(task);
            emptyWaitSet.signal();
        } finally {
            lock.unlock();
        }
    }
    /*带超时的阻塞添加*/
    public boolean offer(T task,long timeout,TimeUnit unit){
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            while (queue.size() == capacity){
                try {
                    if (nanos <= 0){
                        return false;
                    }
                    nanos = fullWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(task);
            emptyWaitSet.signal();
            return true;
        }finally {
            lock.unlock();
        }
    }
    /*获取大小*/
    public int size(){
        return 0;
    }

    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();
        try {
            //判断队列是否已满
            if (queue.size() == capacity){
                rejectPolicy.reject(this,task);
            }else {
                queue.addLast(task);
                emptyWaitSet.signal();
            }
        }finally {
            lock.unlock();
        }
    }
}
