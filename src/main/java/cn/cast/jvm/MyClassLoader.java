package cn.cast.jvm;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * description
 *
 * @author 周德永
 * @date 2021/12/12 23:39
 */
public class MyClassLoader extends ClassLoader {
    private String byteCodePath;

    public MyClassLoader(String byteCodePath) {
        this.byteCodePath = byteCodePath;
    }

    public MyClassLoader(ClassLoader parent, String byteCodePath) {
        super(parent);
        this.byteCodePath = byteCodePath;
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        BufferedInputStream bis = null;
        ByteArrayOutputStream baos = null;

        try {
            /*获取字节码文件的完整路劲*/
            String fileName = byteCodePath+className+".class";
            /*获取一个输入流*/
            bis = new BufferedInputStream(new FileInputStream(fileName));
            /*获取一个输出流*/
            baos = new ByteArrayOutputStream();
            /*具体读入数据并写出的过程*/

            int len;
            byte[] data = new byte[1024];
            while ((len = bis.read(data)) != -1){
                baos.write(data,0,len);
            }

            byte[] byteCode = baos.toByteArray();
            return defineClass(null, byteCode, 0, byteCode.length);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }finally {
            try {
                if (baos != null){
                    baos.close();
                }
                if (bis != null){
                    bis.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            MyClassLoader loader = new MyClassLoader("D:\\pic\\JVM\\");
            Class<?> hanoi = loader.loadClass("Hanoi");
            System.out.println("加载此类的类加载器为"+hanoi.getClassLoader().getClass().getName());
            System.out.println("加载此类的类加载器的父类加载器为"+hanoi.getClassLoader().getParent().getClass().getName());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
