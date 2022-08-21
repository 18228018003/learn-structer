package cn.cast.work;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * description
 *
 * @author 周德永
 * @date 2021/12/17 21:50
 */
public class MainTest {
    public static void main(String[] args) {
        Method[] methods = Test.class.getMethods();
        Method[] fatherMethods = Test.class.getSuperclass().getMethods();

        List<Method> methodList = Arrays.asList(methods);
        List<Method> fatherList = Arrays.asList(fatherMethods);

        System.out.println("=============================");
        System.out.println();
        System.out.println();
        List<Method> list = new ArrayList<>();
        methodList.forEach(method -> {
            if (!fatherList.contains(method)){
               list.add(method);
            }
        });

        System.out.println("=============================");
        System.out.println();
        System.out.println();
        Method[] m1s = new Method[list.size()];
        for (int i = 0; i < list.size(); i++) {
            m1s[i] = list.get(i);
        }

        for (Method m1 : m1s) {
            System.out.println(m1);
        }
    }

}
