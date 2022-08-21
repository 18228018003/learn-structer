package cn.cast.jvm.thread;

public class ThreadState {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("running");
        }, "t1");

        Thread t2 = new Thread(() -> {
            while (true){

            }
        }, "t2");
        t2.start();

        Thread t3 = new Thread(() -> {
            System.out.println("running");
        }, "t3");
        t3.start();

        Thread t4 = new Thread(() -> {
            synchronized (ThreadState.class){
                try {
                    Thread.sleep(100000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "t4");
        t4.start();

        Thread t5 = new Thread(() -> {
            try {
                t2.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }, "t5");
        t5.start();

        Thread t6 = new Thread(() -> {
            synchronized (ThreadState.class){
                try{
                    Thread.sleep(1000000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }, "t6");
        t6.start();

        try{
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("t1 state {} "+ t1.getState()); //NEW
        System.out.println("t2 state {} "+ t2.getState()); //RUNNING
        System.out.println("t3 state {} "+ t3.getState()); //TERMINATED
        System.out.println("t4 state {} "+ t4.getState()); //TIMED_WAITING
        System.out.println("t5 state {} "+ t5.getState()); //WAITING
        System.out.println("t6 state {} "+ t6.getState()); //BLOCKED

    }
}
