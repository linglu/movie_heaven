package com.example;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by linky on 16-2-21.
 * Java8 Stream 特性
 */
public class Java8Stream {

    public static void main(String[] args) {
        // 数值流的构造
//        IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
//        IntStream.range(1,3).forEach(System.out::println);
//        IntStream.rangeClosed(1, 3).forEach(System.out::println);

        // 映射，一对一
//        toUpperCase();
//        square();

        // 映射一对多
//        flatMap();

        // 过滤
//        even();

        // peak
//        peek();

        // Optional
//        findFirst();

        // Reduce
        reduce();

    }

    /**
     * 把 Stream 元素组合起来，多个变成一个
     */
    public static void reduce() {
        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        System.out.println("concat : " + concat);

        // 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        System.out.println(" minValue : " + minValue);

        // 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        System.out.println(" sumValue : " + sumValue);

        // 求和，sumValue = 10, 无起始值
        Optional<Integer> sum = Stream.of(1, 2, 3, 4).reduce(Integer::sum);
        System.out.println(" sumValue : " + sumValue);

        // 过滤，字符串连接，concat = "ace"
        concat = Stream.of("a", "B", "c", "D", "e", "F").
                filter(x -> x.compareTo("Z") > 0).
                reduce("", String::concat);

        System.out.println(" concat : " + concat);
    }

    /**
     *
     */
    public static void findFirst() {
        String strA = " abcd ", strB = null;
        print(strA);
        print("");
        print(strB);
        getLength(strA);
        getLength("");
        getLength(strB);

    }

    public static void print(String text) {
        // Java 8
        Optional.ofNullable(text).ifPresent(System.out::println);
        // Pre-Java 8
        if (text != null) {
            System.out.println(text);
        }
    }
    public static int getLength(String text) {
        // Java 8
        return Optional.ofNullable(text).map(String::length).orElse(-1);

        // Pre-Java 8
//        return if (text != null) ? text.length() : -1;
    };

    /**
     * peak 可以让一个 Stream 实现多次执行 terminal 操作
     */
    public static void peek() {
        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    /**
     * 留下偶数
     */
    public static void even() {
        IntStream.rangeClosed(1, 6).filter(n -> n % 2 == 0).forEach(System.out::println);
    }

    /**
     * 扁平化
     */
    public static void flatMap() {
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1,7), Arrays.asList(2, 3), Arrays.asList(4, 5, 6)
        );
        inputStream.flatMap(Collection::stream)
        .forEach(System.out::println);
    }

    /**
     * 平方数
     */
    public static void square() {
        Arrays.asList(1, 2, 3, 4).stream().
                map(n -> n * n).
                collect(Collectors.toList())
        .forEach(System.out::println);
    }

    /**
     * 转换大小写
     */
    public static void toUpperCase() {
        Arrays.asList("a", "b", "c", "d").stream().map(String::toUpperCase)
                .collect(Collectors.toList()).forEach(System.out::println);
    }
}
