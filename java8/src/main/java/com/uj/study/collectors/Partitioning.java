package com.uj.study.collectors;

import com.uj.study.model.lambdasinaction.Dish;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.uj.study.model.lambdasinaction.Dish.menu;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/27 下午5:20
 * @description：
 * 分区是分组的特殊情况:由一个谓词(返回一个布尔值的函数)作为分类函数,它称分区函
 * 数。分区函数返回一个布尔值,这意味着得到的分组Map的键类型是Boolean,于是它最多可以
 * 分为两组——true是一组, false是一组。
 * @modified By：
 * @version:
 */
public class Partitioning {

    /**
     * partitioningBy
     * @return
     */
    private static Map<Boolean, List<Dish>> partitionByVegeterian() {
        return menu.stream().collect(partitioningBy(Dish::isVegetarian));
    }

    /**
     * {false={FISH=[prawns, salmon], MEAT=[pork, beef, chicken]},
     * true={OTHER=[french fries, rice, season fruit, pizza]}}
     * @return
     */
    private static Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType() {
        return menu.stream().collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
    }

    private static Map<Boolean, Dish> mostCaloricPartitionedByVegetarian() {
        return menu.stream().collect(
                partitioningBy(Dish::isVegetarian,
                        collectingAndThen(
                                maxBy(comparingInt(Dish::getCalories)),
                                Optional::get)));
    }
}
