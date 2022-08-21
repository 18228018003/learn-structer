package cn.cast.jvm.thread;

import java.util.Random;

class Account{
    private int money;

    public Account(int money){
        this.money = money;
    }

    public int getMoney(){
        return money;
    }

    public void setMoney(int money){
        this.money = money;

    }

    public void transfer(Account target,int amount){
        synchronized (Account.class){
            if (this.money >= amount){
                this.setMoney(money - amount);
                target.setMoney(target.getMoney() + amount);
            }
        }
    }
}
public class ExerciseTransfer {
    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(1000);
        Account b = new Account(1000);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                a.transfer(b, randomAmount());
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                b.transfer(a, randomAmount());
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("转账之后金额汇总："+(a.getMoney()+ b.getMoney()));
    }

    static Random random = new Random();
    private static int randomAmount() {
        return random.nextInt(100) + 1;
    }
}
