package com.uj.study.collectors;

import com.uj.study.model.lambdasinaction.Dish;

import static com.uj.study.model.lambdasinaction.Dish.*;
import static java.util.stream.Collectors.reducing;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/21 下午1:35
 * @description：
 * @modified By：
 * @version:
 */
public class Reducing {
    private static int calculateTotalCalories() {
        return menu.stream().collect(reducing(0, //初始值
                Dish::getCalories, //转换函数
                (Integer i, Integer j) -> i + j)); //累积函数
    }

    private static int calculateTotalCaloriesWithMethodReference() {
        return menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
    }

    private static int calculateTotalCaloriesWithoutCollectors() {
        return menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
    }

    private static int calculateTotalCaloriesUsingSum() {
        return menu.stream().mapToInt(Dish::getCalories).sum();
    }
}
