package cn.cast.jvm.thread;

import java.util.LinkedList;

/*生产者消费者模式*/
public class ProducerConsumer  {
    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue(2);
        int count = 0;
        for (int i = 0; i < 3; i++) {
            final int temp = count;
            new Thread(()->{
                while (true){
                    queue.put(new Message(temp,"值"+temp));
                }
            },"生产者："+i).start();
        }
        new Thread(()->{
            while (true){
                Message take = queue.take();
                System.out.println(take);
            }
        },"消费者").start();
    }
}
/*消息队列类 java线程间通信*/
class MessageQueue{
    /*消息的队列集合*/
    private LinkedList<Message> list = new LinkedList<>();
    /*队列容量*/
    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    //获取消息
    public Message take(){
        /*检查队列是否为空*/
        synchronized (list){
           while (list.isEmpty()){
               try {
                   list.wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
           Message message = list.removeFirst();
           list.notifyAll();
           return message;/*从队列的头部获取消息返回*/
        }
    }
    public void put(Message message){
        synchronized (list){
            /*检查队列是够已满*/
            while (list.size() >= capacity){
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.addLast(message);
            list.notifyAll();
        }
    }
}
final class Message{
    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}