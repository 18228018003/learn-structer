package cn.cast.jvm.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 保护性暂停
 * 应用场景 将一个线程的东西传递给另一个线程
 */
class DownLoader{
    public static List<String> download() throws IOException{
        HttpURLConnection conn = (HttpURLConnection) new URL("https://www.baidu.com/").openConnection();
        List<String> lines = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))){
            String line;
            while ((line = reader.readLine()) != null){
                lines.add(line);
            }
        }
        return lines;
    }
}
class GuardedTest{
    public static void main1(String[] args) {
        GuardedObject guardedObject = new GuardedObject(1);
        new Thread(()->{
            List<String> list = (List<String>) guardedObject.get(2000);
            System.out.println(list);
        },"t1").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"执行下载");
            try{
                List<String> list = DownLoader.download();
                guardedObject.complete(list);
            }catch (IOException e){
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new People().start();
        }
        Thread.sleep(1000);
        for (int id : MailBoxes.getIds()) {
            new Postman(id,"内容"+id).start();
        }
    }
}
class People extends Thread{
    @Override
    public void run() {
        /*收信*/
        GuardedObject guardedObject = MailBoxes.createGuardedObject();
        System.out.println(Thread.currentThread().getName()+",开始收信 id："+guardedObject.getId());
        Object mail = guardedObject.get(5000);
        System.out.println(Thread.currentThread().getName()+",收到信 id："+guardedObject.getId() + "信件内容：" + mail);
    }
}
class Postman extends Thread{
    private int id;
    private String mail;
    public Postman(int id,String mail){
        this.id = id;
        this.mail = mail;
    }
    @Override
    public void run() {
        GuardedObject guardedObject = MailBoxes.getGuardedObject(id);
        System.out.println(Thread.currentThread().getName()+",开始送信 id："+guardedObject.getId());
        guardedObject.complete(mail);
        System.out.println(Thread.currentThread().getName()+",送信 id："+guardedObject.getId() + "信件内容：" + mail);

    }
}
class MailBoxes{
    private static Map<Integer,GuardedObject> boxes = new ConcurrentHashMap<>();

    private static int id = 1;

    /*产生唯一id*/
    public static synchronized int generateId(){
        return id++;
    }

    public static GuardedObject getGuardedObject(int id){
        return boxes.remove(id);
    }

    public static GuardedObject createGuardedObject(){
        GuardedObject object = new GuardedObject(generateId());
        boxes.put(object.getId(),object);
        return object;
    }

    public static Set<Integer> getIds(){
        return boxes.keySet();
    }
}
public class GuardedObject {
    /*标识guardedObject*/
    private int id;
    /*结果*/
    private Object response;

    public GuardedObject(int id) {
        this.id = id;
    }

    //获取结果
    //timeout 等待时间
    public Object get(long timeout){
        synchronized (this){
            //记录开始等待时间
            long begin = System.currentTimeMillis(); //15:00
            //经历的时间
            long passedTime = 0;
            while (response == null){
                long waitTime = timeout - passedTime;
                /*经历的时间超过了最大等待时间，退出循环*/
                if (passedTime >= timeout) break;
                try {
                    /*考虑被其他线程打断，或者虚假唤醒的情况
                    唤醒之后response 还是为空
                    */
                    this.wait(waitTime); //15:01被打断 下次再来 只需要等一秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*求得经历时间*/
                passedTime = System.currentTimeMillis() - begin; //15:02

            }
            return response;
        }
    }
    //产生结果
    public void complete(Object response){
        synchronized (this){
            this.response = response;
            this.notify();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

class Test12{
    public Object res;

    public Object getRes(long timeout){
        synchronized (this){
            long begin = System.currentTimeMillis();
            long passedTime = 0;
            while (res == null){
                long waitTime = timeout - passedTime;
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passedTime = System.currentTimeMillis() - begin;
            }
            return res;
        }
    }

    public void complete(Object res){
        synchronized (this){
            this.res = res;
            this.notify();
        }
    }
}
