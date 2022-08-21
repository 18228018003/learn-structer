package cn.cast.jvm.sycn;
/**
 * 可重入锁（也叫递归锁）
 * 指的是同一线程外层函数获得锁之后，内层递归函数仍然能获取到该锁的代码，在同一线程在外层方法获取锁的时候，在进入内层方法会自动获取锁
 *
 * 也就是说：线程可以进入任何一个它已经拥有的锁所同步的代码块
 */
class Phone{
    /**
     * 发送短信
     */
    public synchronized void sendSMS(){
        System.out.println(Thread.currentThread().getName() + "\t invoked sendSMS()");
        // 在同步方法中，调用另外一个同步方法
        sendEmail();
    }
    /**
     * 发邮件
     */
    private synchronized void sendEmail() {
        System.out.println(Thread.currentThread().getName() + "\t invoked sendEmail()");
    }
}
public class ReenterLockDemo {
    public static void main(String[] args) {
        Phone phone = new Phone();
        // 两个线程操作资源列
        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t1").start();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}
