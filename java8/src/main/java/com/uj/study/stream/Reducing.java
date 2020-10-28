package com.uj.study.stream;

import com.uj.study.model.lambdasinaction.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/16 下午1:11
 * @description：
 * @modified By：
 * @version:
 */
public class Reducing {

    /**
     * reduce
     * @param args
     */
    public static void main(String...args){

        List<Integer> numbers = Arrays.asList(3,4,5,1,2);
        int sum = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);

        int sum2 = numbers.stream().reduce(0, Integer::sum);
        System.out.println(sum2);

        int max = numbers.stream().reduce(0, (a, b) -> Integer.max(a, b));
        System.out.println(max);

        Optional<Integer> min = numbers.stream().reduce(Integer::min);
        min.ifPresent(System.out::println);

        int calories = Dish.menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
        System.out.println("Number of calories:" + calories);
    }

    /**
     * 怎样用map和reduce方法数一数流中有多少个菜呢?
     * 答案:要解决这个问题,你可以把流中每个元素都映射成数字1,然后用reduce求和。这
     * 相当于按顺序数流中的元素个数。
     */
    public static void mapReduce() {
        int count = Dish.menu.stream()
                .map(d -> 1)
                .reduce(0, (a, b) -> a + b);

    }
}
