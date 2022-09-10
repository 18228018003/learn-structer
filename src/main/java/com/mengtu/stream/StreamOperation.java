package com.mengtu.stream;

import org.junit.Test;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamOperation {
    @Test
    public void getAppleColor() {
        List<Apple> list = new ArrayList<>();
        list.add(new Apple(1, "red", "四川"));
        list.add(new Apple(2, "green", "青海"));
        list.add(new Apple(3, "green", "浙江"));
        list.add(new Apple(4, "blue", "江苏"));
        list.add(new Apple(5, "red", "云南"));
        list.stream().filter(apple -> apple.getColor().equals("red"))
                .sorted(Comparator.comparingInt(Apple::getId))
                .distinct()
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    @Test
    public void testStreamGeneric() {
        //生成stream流的第一种方式
//        Stream<Integer> stream = Stream.of(1,2,3,4,5,6,7,8,9,10).map(x->x*2);
//        stream.forEach(System.out::print);

        //第二种方式，参数1：开始值 参数二：以开始值为种子计算出来的新值
//        Stream.iterate(0, (x) -> x + 2).limit(8).collect(Collectors.toList()).forEach(System.out::print);

        //生成stream流的第三种方式
        Stream<Double> stream = Stream.generate(Math::random).limit(100);
        long count = stream.count();
        System.out.println(count);
    }

    @Test
    public void testPattern() {
        Pattern pattern = Pattern.compile(",");
        Stream<String> stream = pattern.splitAsStream("tom,jack,jerry,john,zero,jane");
        stream.forEach(System.out::println);
    }

    @Test
    public void testFilter() {
        List<String> list = Arrays.asList("1", "22", "aaa", "fff", "ddd", "eee", "ggg", "hhhhhh");
        list.stream().filter(s -> s.length() >= 3).forEach(System.out::println);

        //验证整个流只遍历一次
        Stream.of(1, 2, 3, 4, 5, 6, 7).filter(i -> {
            System.out.println("filter的元素：" + i);
            return i > 1;
        }).filter(i -> {
            System.out.println("filter2的元素：" + i);
            return i == 5;
        }).forEach(i -> System.out.println("最后结果：" + i));
    }

    @Test
    public void testLimitSkipAnd() {
        List<String> list = Arrays.asList("11", "22", "33", "44", "55", "66", "77", "88");
//        list.stream().limit(3).forEach(System.out::println);
        list.stream().skip(0).limit(3).forEach(System.out::println);
        list.stream().skip(3).limit(3).forEach(System.out::println);
        list.stream().skip(6).limit(3).forEach(System.out::println);
    }

    @Test
    public void testDistinct() {
        List<Student> list = Arrays.asList(
                new Student(1, "张三"),
                new Student(2, "张三"),
                new Student(3, "李四"),
                new Student(4, "王五"),
                new Student(5, "李四")
        );
        list.stream().distinct().forEach(System.out::println);
    }

    @Test
    public void testMapAndFlatMap() {
//        List<String> list = Arrays.asList("a,b,c,d","1,2,3,4");
//        list.stream().map(s -> s.replaceAll(",","-")).forEach(System.out::println);
//
//        System.out.println("=======================");
//
//        list.stream().flatMap(s -> {
//            String[] split = s.split(",");
//            return Arrays.stream(split);
//        }).forEach(System.out::println);
//
//        System.out.println("=======================");
        List<List<String>> nestList = Arrays.asList(
                Arrays.asList("a", "b", "c"),
                Arrays.asList("d", "e", "f"),
                Arrays.asList("h", "j", "k")
        );

        nestList.stream().flatMap(Collection::stream).forEach(System.out::println);
    }

    @Test
    public void testPeek() {
        List<Student> list = Arrays.asList(
                new Student(1, "张三"),
                new Student(2, "张磊"),
                new Student(3, "李四"),
                new Student(4, "王五"),
                new Student(5, "赵六")
        );
        list.stream().peek(student -> {
            if (student.getName().equals("王五")) {
                student.setName("123");
            }
        }).forEach(System.out::println);
    }

    @Test
    public void testMatch() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        boolean b = list.stream().allMatch(e -> e < 4);
        System.out.println(b);

        System.out.println("==============");
        boolean b1 = list.stream().anyMatch(e -> e < 4);
        System.out.println(b1);

        Optional<Integer> first = list.stream().findFirst();
        Integer integer = first.get();
        System.out.println(integer);

        System.out.println(" ============== ");

        Optional<Integer> any = list.stream().findAny();
        Integer i = first.get();
        System.out.println(integer);
    }

    @Test
    public void testCountMaxMin() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        Integer max = list.stream().max(Integer::compareTo).get();
        System.out.println(max);

        System.out.println("==========================");
        Integer min = list.stream().min(Integer::compareTo).get();
        System.out.println(min);
    }

    @Test
    public void testReduce() {
        List<Integer> list = Stream.iterate(1, i -> i + 1).limit(10).collect(Collectors.toList());

        Integer integer = list.stream().reduce(Integer::sum).get();
        System.out.println(integer);
        System.out.println("==================");

        Integer reduce = list.stream().reduce(10, Integer::sum);
        System.out.println(reduce);
        System.out.println("==================");

        Integer reduce1 = list.stream().reduce(0, Integer::sum, Integer::sum);
        System.out.println(reduce1);
    }

    @Test
    public void testParallelStreamReduce() {
//        IntStream stream = Stream.generate(Math::random).limit(1000000).mapToInt(Double::intValue);
        List<Integer> list = Stream.iterate(1, i -> i + 1).limit(1000000).collect(Collectors.toList());
        long l = System.currentTimeMillis();
        System.out.println(list.parallelStream().reduce(0, Integer::sum, Integer::sum));
        long end = System.currentTimeMillis() - l;
        System.out.println(end);

        long start = System.currentTimeMillis();
        Integer reduce = list.stream().reduce(0, Integer::sum, Integer::sum);
        System.out.println(reduce);
        long en = System.currentTimeMillis() - start;
        System.out.println(en);
    }

    @Test
    public void testCollect() {
        Stream<String> stringStream = Stream.of("aa", "bb", "cc", "dd", "bb");
        List<String> list = stringStream.collect(Collectors.toList());
        System.out.println(list);

        Set<String> set = Stream.of("aa", "bb", "cc", "dd", "bb").collect(Collectors.toSet());
        System.out.println(set);

        LinkedList<String> linkedList = Stream.of("aa", "bb", "cc", "dd", "ee").collect(Collectors.toCollection(LinkedList::new));
        System.out.println(linkedList);

        TreeSet<String> treeSet = Stream.of("aa", "bb", "cc", "dd", "ee").collect(Collectors.toCollection(TreeSet::new));
        System.out.println(treeSet);
    }

    @Test
    public void testCollectJoining() {
        //连接操作
        String string = Stream.of("aa", "bb", "cc", "dd", "ee").collect(Collectors.joining());
        System.out.println(string);

        //连接操作 指定分隔符
        String str = Stream.of("aa", "bb", "cc", "dd", "ee").collect(Collectors.joining("-"));
        System.out.println(str);

        /*连接操作，指定前后缀*/
        String str1 = Stream.of("aa", "bb", "cc", "dd", "ee").collect(Collectors.joining("-", "<", ">"));
        System.out.println(str1);
    }

    @Test
    public void testCollectGroupBy() {
        Map<String, List<Person>> map = Stream.of(
                new Person("张三", "男", 23),
                new Person("李四", "男", 21),
                new Person("王五", "男", 25),
                new Person("赵六", "男", 27),
                new Person("小丽", "女", 27),
                new Person("小花", "女", 24)
        ).collect(Collectors.groupingBy(Person::getGender));
        System.out.println(map.get("女"));
        System.out.println(map.get("男"));

        System.out.println("===================");

        Map<String, List<String>> result = Stream.of(
                new Person("张三", "男", 23),
                new Person("李四", "男", 21),
                new Person("王五", "男", 25),
                new Person("赵六", "男", 27),
                new Person("小丽", "女", 27),
                new Person("小花", "女", 24)
        ).collect(Collectors.groupingBy(Person::getGender, Collectors.mapping(Person::getName, Collectors.toList())));

        System.out.println(result.get("女"));
        System.out.println(result.get("男"));
        System.out.println("===================");

        Map<String, Optional<Integer>> collect = Stream.of(
                new Person("张三", "男", 23),
                new Person("李四", "男", 21),
                new Person("王五", "男", 25),
                new Person("赵六", "男", 27),
                new Person("小丽", "女", 27),
                new Person("小花", "女", 24)
        ).collect(Collectors.groupingBy(Person::getGender,
                Collectors.mapping(
                        Person::getAge,
                        Collectors.maxBy(Integer::compareTo))));
        System.out.println(collect.get("女").get());
        System.out.println(collect.get("男").get());
        System.out.println("===================");


    }

    @Test
    public void testCollectGroupBy2() {
        Map<String, Optional<Person>> collect = Stream.of(
                new Person("张三", "男", 23),
                new Person("李四", "男", 21),
                new Person("王五", "男", 25),
                new Person("赵六", "男", 27),
                new Person("小丽", "女", 27),
                new Person("小花", "女", 24)
        ).collect(Collectors.groupingBy(Person::getGender,
                Collectors.reducing(BinaryOperator.maxBy(Comparator.comparingInt(Person::getAge)))));
        System.out.println(collect.get("女").get());
        System.out.println(collect.get("男").get());
        System.out.println("===================");

        Map<String, Integer> map = Stream.of(
                new Person("张三", "男", 23),
                new Person("李四", "男", 21),
                new Person("王五", "男", 25),
                new Person("赵六", "男", 27),
                new Person("小丽", "女", 27),
                new Person("小花", "女", 24)
        ).collect(Collectors.groupingBy(Person::getGender,
                Collectors.reducing(0, Person::getAge, (x, y) -> x + y)));
        System.out.println(map.get("女"));
        System.out.println(map.get("男"));
        System.out.println("===================");

        Map<String, Integer> mapSum = Stream.of(
                new Person("张三", "男", 23),
                new Person("李四", "男", 21),
                new Person("王五", "男", 25),
                new Person("赵六", "男", 27),
                new Person("小丽", "女", 27),
                new Person("小花", "女", 24)
        ).collect(Collectors.groupingBy(Person::getGender,
                Collectors.summingInt(Person::getAge)));
        System.out.println(mapSum.get("女"));
        System.out.println(mapSum.get("男"));
        System.out.println("===================");

        Map<String, Double> mapAvg = Stream.of(
                new Person("张三", "男", 23),
                new Person("李四", "男", 21),
                new Person("王五", "男", 25),
                new Person("赵六", "男", 27),
                new Person("小丽", "女", 27),
                new Person("小花", "女", 24)
        ).collect(Collectors.groupingBy(Person::getGender,
                Collectors.averagingInt(Person::getAge)));
        System.out.println(mapAvg.get("女"));
        System.out.println(mapAvg.get("男"));
        System.out.println("===================");

        Map<String, ArrayList<Person>> listMap = Stream.of(
                new Person("张三", "男", 23),
                new Person("李四", "男", 21),
                new Person("王五", "男", 25),
                new Person("赵六", "男", 27),
                new Person("小丽", "女", 27),
                new Person("小花", "女", 24)
        ).collect(Collectors.toMap(Person::getName, p -> {
            ArrayList<Person> personList = new ArrayList<>();
            personList.add(p);
            return personList;
        }));
        System.out.println(listMap);
    }
}
