package cn.cast.jvm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * description
 *
 * @author 周德永
 * @date 2021/12/12 23:59
 */
public class LoopRun {
    public static void main(String[] args){
        while (true){
            try {
                MyClassLoader loader = new MyClassLoader("D:\\develop\\learn-structer\\src\\main\\java\\cn\\cast\\recur\\");
                Class<?> clazz = loader.findClass("MyTest");
                Object obj = clazz.newInstance();
                Method hanoi = clazz.getMethod("test");
                hanoi.invoke(obj);
                Thread.sleep(4000);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
