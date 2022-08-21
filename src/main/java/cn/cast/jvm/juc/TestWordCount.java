package cn.cast.jvm.juc;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/*单词计数*/
public class TestWordCount {
    static final String ALPHA = "abcedfghijklmnopqrstuvwxyz";
    public static void main2(String[] args) {
        int length = ALPHA.length();
        int count = 200;
        List<String> list = new ArrayList<>(length * count);
        for (int i = 0; i < length; i++) {
            File file = new File("\\tem"+(i+1)+".txt");

            char ch = ALPHA.charAt(i);
            for (int j = 0; j < count; j++) {
                list.add(String.valueOf(ch));
            }
        }
        Collections.shuffle(list);
        for (int i = 0; i < 26; i++) {
            try (PrintWriter out = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream("D:\\develop\\learn-structer\\src\\main\\java\\cn\\cast\\jvm\\juc\\tmp" + (i+1) + ".txt")))) {
                String collect = String.join("\n", list.subList(i * count, (i + 1) * count));
                out.print(collect);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {

        demo(()->new ConcurrentHashMap<String, LongAdder>(),
                (map,words)->{
                    for (String word : words) {
                        LongAdder value = map.computeIfAbsent(word, val -> new LongAdder());
                        value.increment();
                    }
                });
    }
    public static List<String> readFromFile(int i) {
        ArrayList<String> words = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                new FileInputStream("D:\\develop\\learn-structer\\src\\main\\java\\cn\\cast\\jvm\\juc\\tmp"
                + i +".txt")))) {
            while(true) {
                String word = in.readLine();
                if(word == null) {
                    break;
                }
                words.add(word);
            }
            return words;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static <V> void demo(Supplier<Map<String,V>> supplier, BiConsumer<Map<String,V>, List<String>> consumer){
        Map<String, V> counterMap = supplier.get();
        List<Thread> ts = new ArrayList<>();
        for (int i = 1; i <= 26; i++) {
            int idx = i;
            Thread thread = new Thread(()->{
                List<String> words = readFromFile(idx);
                consumer.accept(counterMap,words);
            });
            ts.add(thread);
        }
        ts.forEach(Thread::start);
        ts.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(counterMap);
    }
}
