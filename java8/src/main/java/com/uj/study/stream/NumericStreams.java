package com.uj.study.stream;

import com.uj.study.model.lambdasinaction.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.uj.study.model.lambdasinaction.Dish.*;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/19 下午1:10
 * @description：
 * @modified By：
 * @version:
 */
public class NumericStreams {
    List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);

    public void printNumbers() {
        Arrays.stream(numbers.toArray()).forEach(System.out::println);
    }

    /**
     * mapToInt
     * 映射到数值流,节省了装箱拆箱的成本
     */
    public void mapToInt() {
        int calories = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();
        System.out.println("Number of calories:" + calories);
    }

    /**
     * boxed()
     * 转换回对象流
     */
    public void boxed() {
        IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
        Stream<Integer> stream = intStream.boxed();
    }

    /**
     * 默认值OptionalInt
     */
    public void optionalInt() {
        OptionalInt maxCalories = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();

        int max;
        if (maxCalories.isPresent()) {
            max = maxCalories.getAsInt();
        } else {
            // we can choose a default value
            max = 1;
        }
        System.out.println(max);
    }

    /**
     * 数值范围
     */
    public void range() {
        // numeric ranges
        IntStream evenNumbers = IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0);
        System.out.println(evenNumbers.count());
    }

    /**
     * 数值流应用:勾股数
     * 表示三元数,比如new int[]{3, 4, 5},来表示勾股数(3, 4, 5)
     * <p>
     * 筛选成立的组合
     * 假定有人为你提供了三元数中的前两个数字: a和b。怎么知道它是否能形成一组勾股数呢?
     * 你需要测试a * a + b * b的平方根是不是整数,也就是说它没有小数部分——在Java里可以
     * 使用expr % 1表示。如果它不是整数,那就是说c不是整数。
     * filter(b -> Math.sqrt(a*a + b*b) % 1 == 0)
     * <p>
     * 生成三元组
     * 在筛选之后,你知道a和b能够组成一个正确的组合。现在需要创建一个三元组
     * .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)});
     */
    public void pythagoreanTriples() {
        Stream<int[]> pythagoreanTriples =
                IntStream.rangeClosed(1, 100).boxed() //创建一个从1到100的数值范围来生成a的值,boxed(): IntStream->Stream<Integer>
                        .flatMap( //要是把a的值映射到三元数流的话,就会得到一个由流构成的流。 flatMap方法在做映射的同时,还会把所有生成的三元数流扁平化成一个流
                                a -> IntStream.rangeClosed(a, 100) //我们把b的范围改成了a 到100。没有必要再从1开始了,否则就会造成重复的三元数,例如(3,4,5)和(4,3,5)
                                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                                        .boxed() //从 rangeClosed 返 回 的 IntStream 生 成 一 个Stream<Integer>
                                        /*
                                        如果不用boxed(),就要用mapToObject():
                                        IntStream.rangeClosed(1, 100)
                                            .filter(b -> Math.sqrt(a*a + b*b) % 1 == 0)
                                            .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)});
                                         */
                                        .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                        );

        pythagoreanTriples.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
    }

    /**
     * 上例做了两次求平方根
     * 让代码更为紧凑的一种可能的方法是,先生成所有的三元数(a*a, b*b, a*a+b*b),然后再筛选符合条件的
     */
    public void betterPythagoreanTriples2() {
        Stream<double[]> pythagoreanTriples2 =
                IntStream.rangeClosed(1, 100).boxed()
                        .flatMap(a -> IntStream.rangeClosed(a, 100)
                                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                                        .filter(t -> t[2] % 1 == 0));

    }

}
