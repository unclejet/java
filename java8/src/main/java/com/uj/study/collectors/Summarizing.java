package com.uj.study.collectors;

import com.uj.study.model.lambdasinaction.Album;
import com.uj.study.model.lambdasinaction.Dish;
import com.uj.study.model.lambdasinaction.Transaction;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.uj.study.model.lambdasinaction.Dish.menu;
import static java.util.stream.Collectors.*;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/21 下午1:04
 * @description：
 * @modified By：
 * @version:
 */
public class Summarizing {
    /**
     *  Collector接口中方法的实现决定了如何对流执行归约操作
     *  最直接和最常用的收集器是toList
     * 静态方法,它会把流中所有的元素收集到一个List中
     */
    public static void f1() {
        Stream<Transaction> transactionStream = Stream.of(new Transaction(null, 0 , 0));
        List<Transaction> transactions =
                transactionStream.collect(Collectors.toList());
    }

    /**
     * 用reduce方法来实现toList Collector所做的工作
     */
    public static void f2() {
        Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5, 6).stream();
        List<Integer> numbers = stream.reduce(
                new ArrayList<Integer>(),
                (List<Integer> l, Integer e) -> {
                    l.add(e);
                    return l; },
                (List<Integer> l1, List<Integer> l2) -> {
                    l1.addAll(l2);
                    return l1; });

    }

    /**
     * Collectors.counting
     * 数一数菜单里有多少
     * 种菜
     */
    private static void howManyDishes() {
        long howManyDishes = menu.stream().collect(counting());
//        这还可以写得更为直接:
        howManyDishes = menu.stream().count();
    }

    /**
     * 广义的归约汇总
     * Collectors.reducing
     * @return
     */
    private static Dish findMostCaloricDish() {
        return menu.stream().collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)).get();
    }

    /**
     * maxBy()
     */
    public static void findMostCaloricDishUsingComparator() {
        Comparator<Dish> dishCaloriesComparator =
                Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> mostCalorieDish = menu.stream()
                        .collect(maxBy(dishCaloriesComparator));
    }

    /**
     * summingInt
     * @return
     */
    private static int calculateTotalCalories() {
        return menu.stream().collect(summingInt(Dish::getCalories));
    }

    /**
     * reducing()
     * 广义的归约汇总
     */
    public static void totalCalories() {
        int totalCalories = menu.stream().collect(reducing(
                0, Dish::getCalories, (i, j) -> i + j));

    }

    /**
     * averagingInt
     * @return
     */
    private static Double calculateAverageCalories() {
        return menu.stream().collect(averagingInt(Dish::getCalories));
    }

    /**
     * summarizingInt
     * @return
     */
    private static IntSummaryStatistics calculateMenuStatistics() {
        return menu.stream().collect(summarizingInt(Dish::getCalories));
    }

    /**mapToInt()
     * summaryStatistics
     * @param album
     */
    public static void printTrackLengthStatistics(Album album) {
        IntSummaryStatistics trackLengthStats
                = album.getTracks()
                .mapToInt(track -> track.getLength())
                .summaryStatistics();

        System.out.printf("Max: %d, Min: %d, Ave: %f, Sum: %d",
                trackLengthStats.getMax(),
                trackLengthStats.getMin(),
                trackLengthStats.getAverage(),
                trackLengthStats.getSum());
    }

    /**
     * joining工厂方法返回的收集器会把对流中每一个对象应用toString方法得到的所有字符
     * 串连接成一个字符串
     * @return
     */
    private static String getShortMenu() {
        return menu.stream().map(Dish::getName).collect(joining());
    }

    /**
     * joining工厂方法有一个重载版本可以接受元素之间的
     * 分界符,这样你就可以得到一个逗号分隔的菜肴名称列表
     * @return
     */
    private static String getShortMenuCommaSeparated() {
        return menu.stream().map(Dish::getName).collect(joining(", "));
    }
}
