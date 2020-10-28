package com.uj.study.stream;

import com.uj.study.model.lambdasinaction.Dish;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.uj.study.model.lambdasinaction.Dish.*;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/14 下午1:10
 * @description：
 * @modified By：
 * @version:
 */
public class IntroduceStream {
    /**
     * 之前(Java 7)
     *  :
     */
    public static void java7() {
        //用累加器筛选元素
        List<Dish> lowCaloricDishes = new ArrayList<>();
        for(Dish d: menu){
            if(d.getCalories() < 400){
                lowCaloricDishes.add(d);
            }

        }
        //用匿名类对菜肴排序
        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            public int compare(Dish d1, Dish d2){
                return Integer.compare(d1.getCalories(), d2.getCalories());
            }
        });
        List<String> lowCaloricDishesName = new ArrayList<>();
        //处理排序后的菜名列表
        for(Dish d: lowCaloricDishes){
            lowCaloricDishesName.add(d.getName());
        }
    }

    /**
     * 之后(Java 8)
     *  :
     */
    public static void java8() {
        List<String> lowCaloricDishesName = menu.stream()
                .filter(d -> d.getCalories() < 400)
                .sorted(comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(toList());
    }

    /**
     * 利用多核架构并行执行
     */
    public static void java8ParallelStream() {
        List<String> lowCaloricDishesName =
                menu.parallelStream()
                        .filter(d -> d.getCalories() < 400)
                        .sorted(comparing(Dish::getCalories))
                        .map(Dish::getName)
                        .collect(toList());
    }

    /**
     * 中间操作
     */
    public static void printStream() {
        List<String> names =
                menu.stream()
                        .filter(d -> {
                            System.out.println("filtering" + d.getName());
                            return d.getCalories() > 300;
                        })
                .map(d -> {
                    System.out.println("mapping" + d.getName());
                    return d.getName();
                })
                .limit(3)
                .collect(toList());
        System.out.println(names);

    }
}
