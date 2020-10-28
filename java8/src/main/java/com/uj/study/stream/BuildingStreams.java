package com.uj.study.stream;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/20 下午1:07
 * @description：
 * @modified By：
 * @version:
 */
public class BuildingStreams {
    /**
     * 空流
     */
    public static void f0() {
        Stream<String> emptyStream = Stream.empty();
    }

    /**
     * Stream.of()
     * 由值创建流
     */
    public static void f1() {
        Stream<String> stream = Stream.of("Java 8", "Lambdas", "In", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);
    }

    /**
     * Arrays.stream()
     * 由数组创建流
     */
    public static void f2() {
        int[] numbers = {2, 3, 5, 7, 11, 13};
        System.out.println(Arrays.stream(numbers).sum());
    }

    /**
     *  由文件生成流
     * @throws IOException
     */
    public static void f3() throws IOException {
        long uniqueWords = Files.lines(Paths.get("lambdasinaction/chap5/data.txt"), Charset.defaultCharset())
                .flatMap(line -> Arrays.stream(line.split(" ")))
                .distinct()
                .count();

        System.out.println("There are " + uniqueWords + " unique words in data.txt");
    }

    /**
     * Stream.iterate
     * 由函数生成流:创建无限流
     * 迭代
     */
    public static void f4() {
        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);

        // fibonnaci with iterate
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1],t[0] + t[1]})
                .limit(10)
                .forEach(t -> System.out.println("(" + t[0] + ", " + t[1] + ")"));

        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1],t[0] + t[1]})
                .limit(10)
                . map(t -> t[0])
                .forEach(System.out::println);
    }

    /**
     * generate不是依次
     * 对每个新生成的值应用函数的。它接受一个Supplier<T>类型的Lambda提供新的值
     */
    public static void f5() {
        // random stream of doubles with Stream.generate
        Stream.generate(Math::random)
                .limit(10)
                .forEach(System.out::println);

        // stream of 1s with Stream.generate
        IntStream.generate(() -> 1)
                .limit(5)
                .forEach(System.out::println);

        IntStream.generate(new IntSupplier(){
            public int getAsInt(){
                return 2;
            }
        }).limit(5)
                .forEach(System.out::println);

        IntSupplier fib = new IntSupplier(){
            private int previous = 0;
            private int current = 1;
            public int getAsInt(){
                int nextValue = this.previous + this.current;
                this.previous = this.current;
                this.current = nextValue;
                return this.previous;
            }
        };
        IntStream.generate(fib).limit(10).forEach(System.out::println);
    }
}
