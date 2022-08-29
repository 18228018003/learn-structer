package com.mengtu.designpattern.pattern.single;

import java.io.IOException;
import java.io.InputStream;

public class RuntimeDemo {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        try {
            Process process = runtime.exec("ipconfig");
            InputStream inputStream = process.getInputStream();
            byte[] arr = new byte[1024 * 1024 * 100];
            int len = inputStream.read(arr);
            System.out.println(new String(arr,0,len,"GBK"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
