package cn.cast.jvm.thread;

/*单例模式*/
public final class Singleton {
    private volatile static Singleton singleton;
    private Singleton(){}
    public static Singleton getInstance(){
        if (singleton != null) {
            return singleton;
        }
        synchronized (Singleton.class){
            if (singleton != null){
                return singleton;
            }
            singleton = new Singleton();
            return singleton;
        }
    }
}
