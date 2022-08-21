package cn.cast.reflect;

import java.lang.reflect.Field;

/**
 * description
 *
 * @author 周德永
 * @date 2022/3/6 18:54
 */
public class Demo1 {
    public static void main(String[] args) {
        //getClazz();
        getFileds();
    }

    private static void getFileds()
    {
        try {
            Class<?> cls1 = Class.forName("cn.cast.reflect.Student");
//            Field[] fields = cls1.getDeclaredFields();
//            Field[] fs2 = cls1.getFields();
//
//            for (Field field : fields) {
//                System.out.println(field);
//            }
            Object o = cls1.newInstance();
            Field name = cls1.getDeclaredField("name");
            Field age = cls1.getDeclaredField("age");
            name.setAccessible(true);
            age.setAccessible(true);
            name.set(o,"张三");
            age.set(o,13);
            System.out.println(o);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    public static void getClazz() {
        try {
            Class<?> cls2 = Class.forName("cn.cast.reflect.Student");
            System.out.println(cls2);

            Class<Student> cls1 = Student.class;
            System.out.println(cls1);

            Student stu = new Student();
            Class<? extends Student> cls3 = stu.getClass();
            System.out.println(cls3);

            System.out.println(cls1==cls2);
            System.out.println(cls3==cls2);
            System.out.println(cls3==cls1);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
