package cn.cast.jvm.sycn;

public class Singleton {
    private static volatile Singleton singleton;
    private Singleton(){
        System.out.println(Thread.currentThread().getName()+"线程调用构造器");
    };
    public static Singleton getInstance(){
        if (singleton == null){
            synchronized (Singleton.class){
                if (singleton == null){
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(Singleton::getInstance,"Thread-"+ i).start();
        }
    }
}
