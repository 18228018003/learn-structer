//package cn.cast.jvm.thread;
//
//import lombok.Data;
//import sun.misc.Unsafe;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//
//public class UnSafeDemo {
//    public static void main121(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
//        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
//        theUnsafe.setAccessible(true);
//        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
//
//        /*1.获取偏移地址*/
//        long idOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
//        long nameOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));
//        /*2.执行cas操作*/
//        Teacher teacher = new Teacher();
//        unsafe.compareAndSwapLong(teacher,idOffset,0,1);
//        unsafe.compareAndSwapObject(teacher,nameOffset,null,"张三");
//        /*3.验证*/
//        System.out.println(teacher);
//    }
//
//    public static void main(String[] args) {
//        Account1.demo(new MyAtomicInteger(10000));
//    }
//}
//class MyAtomicInteger implements Account1 {
//    private volatile int value;
//    private static final long valueOffset;
//    static final Unsafe unsafe;
//    static {
//        unsafe = UnsafeAccessor.getUnsafe();
//        try {
//            valueOffset = unsafe.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//    public MyAtomicInteger(int money) {
//        this.value = money;
//    }
//
//    public int getValue(){
//        return value;
//    }
//
//    public void decrement(int amount){
//        while (true){
//            int prev = this.value;
//            int next = prev-amount;
//            if (unsafe.compareAndSwapInt(this,valueOffset,prev,next)){
//                break;
//            }
//        }
//    }
//    public void increment(int amount){
//        while (true){
//            int prev = this.value;
//            int next = prev-amount;
//            if (unsafe.compareAndSwapInt(this,valueOffset,prev,next)){
//                break;
//            }
//        }
//    }
//    @Override
//    public Integer getBalance() {
//        return getValue();
//    }
//
//    @Override
//    public void withdraw(Integer amount) {
//        decrement(amount);
//    }
//
//
//}
//class UnsafeAccessor{
//    private static final Unsafe unsafe;
//    static {
//        try {
//            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
//            theUnsafe.setAccessible(true);
//            unsafe = (Unsafe) theUnsafe.get(null);
//        }catch (NoSuchFieldException | IllegalAccessException e){
//            throw new Error(e);
//        }
//    }
//    public static Unsafe getUnsafe(){
//        return unsafe;
//    }
//}
//@Data
//class Teacher{
//    private volatile int id;
//    private volatile String name;
//}