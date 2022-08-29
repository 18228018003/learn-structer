package com.learn.factory;

public class Client {
    public static void main(String[] args) {
        Phone phone = new Phone.Builder()
                .cpu("intel")
                .screen("sansang")
                .memory("kingston")
                .mainboard("ausa")
                .build();

        System.out.println(phone);
    }
}
