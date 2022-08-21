package cn.cast.jvm.thread;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class ExerciseSell {
    public static void main(String[] args) {
        TicketWindow ticketWindow = new TicketWindow(2000);
        List<Thread> list = new ArrayList<>();
        /*用于存储卖出多少张票*/
        List<Integer> sellCount = new Vector<>();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int sell = ticketWindow.sell(randomCount());
                sellCount.add(sell);
            });
            list.add(thread);
            thread.start();
        }
        list.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        /*卖出的票求和*/
        System.out.println("selled count : "+ sellCount.stream().mapToInt(c->c).sum());
        /*剩余票数*/
        System.out.println("remainder count : "+ ticketWindow.getCount());
    }
    static Random random = new Random();
    public static int randomCount(){
        return random.nextInt(5)+1;
    }
}
class TicketWindow{
    private int count = 0;

    public TicketWindow(){}

    public TicketWindow(int count){
        this.count = count;
    }

    public synchronized int sell(int cnt){
        if (count >= cnt){
            count -= cnt;
            return cnt;
        }
        return 0;
    }

    public int getCount() {
        return count;
    }
}