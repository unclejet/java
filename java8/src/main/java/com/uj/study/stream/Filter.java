package com.uj.study.stream;

import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;
import com.uj.study.model.lambdasinaction.Dish;

import java.util.Arrays;
import java.util.List;

import static com.uj.study.model.lambdasinaction.Dish.*;
import static java.util.stream.Collectors.toList;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/14 下午1:24
 * @description：
 * @modified By：
 * @version:
 */
public class Filter {
    /**
     * Filtering with predicate
     */
    public static void f1() {
        List<Dish> vegetarianMenu = menu.stream()
                        .filter(Dish::isVegetarian)
                        .collect(toList());

        vegetarianMenu.forEach(System.out::println);
    }

    /**
     * distinct()
     * Filtering unique elements
     */
    public static void f2() {
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * limit()
     * Truncating a stream
     */
    public static void f3() {
        List<Dish> dishesLimit3 = menu.stream()
                        .filter(d -> d.getCalories() > 300)
                        .limit(3)
                        .collect(toList());
    }

    /**
     * skip()
     * Skipping elements
     */
    public static void f4() {
        List<Dish> dishesSkip2 =
                menu.stream()
                        .filter(d -> d.getCalories() > 300)
                        .skip(2)
                        .collect(toList());

        dishesSkip2.forEach(System.out::println);
    }
}
