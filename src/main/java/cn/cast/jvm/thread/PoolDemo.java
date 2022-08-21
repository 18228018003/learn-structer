package cn.cast.jvm.thread;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PoolDemo {
}
interface RejectPolicy1<T>{
    void reject(BlockQueue1<T> taskQueue,T task);
}
class Pool{
    private int coreSize;
    private BlockQueue1<Runnable> taskQueue;
    private HashSet<Worker1> workers = new HashSet<>();
    private long timeout;
    private TimeUnit unit;
    private RejectPolicy1 rejectPolicy;
    public Pool(int coreSize, long timeout, TimeUnit unit,int queueSize,RejectPolicy1 rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.unit = unit;
        this.taskQueue = new BlockQueue1<>(queueSize);
        this.rejectPolicy = rejectPolicy;
    }

    public void execute(Runnable task){
        /*当任务数没有超过coreSize时，直接交给worker 对象执行*/
        /*当超过coreSize时,加入任务队列缓存*/
        synchronized (workers){
            if (workers.size() < coreSize){
                Worker1 worker = new Worker1(task);
                worker.start();
                workers.add(worker);
            }else {
//                taskQueue.put(task);
                /**
                 * 1) 死等
                 * 2) 带超时等待
                 * 3) 让调用者放弃执行任务
                 * 4) 让调用者抛出异常
                 * 5) 让调用者自己执行任务
                 */
                taskQueue.tryPut(task,rejectPolicy);
            }
        }
    }
    class Worker1 extends Thread{
        private Runnable task;

        public Worker1(Runnable task){
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

class BlockQueue1<T>{
    private int capacity;
    private Deque<T> queue = new ArrayDeque<>();
    private ReentrantLock lock = new ReentrantLock();
    private Condition fullWaitSet = lock.newCondition();
    private Condition emptyWaitSet = lock.newCondition();

    public BlockQueue1(int capacity) {
        this.capacity = capacity;
    }
    /*获取大小*/
    public int size(){
        return 0;
    }
    public void tryPut(T task,RejectPolicy1<T> rejectPolicy){
        lock.lock();
        try {
            //判断队列是否已满
           if (queue.size() == capacity){
               rejectPolicy.reject(this,task);
           }else{
               queue.addLast(task);
               emptyWaitSet.signal();

           }
        }finally {
            lock.unlock();
        }
    }

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
        }finally {
            lock.unlock();
        }
    }

    public T poll(long timeout,TimeUnit unit){
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            while (queue.isEmpty()){
                try {
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
}
