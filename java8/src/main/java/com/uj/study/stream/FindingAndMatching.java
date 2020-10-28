package com.uj.study.stream;

import com.uj.study.model.lambdasinaction.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.uj.study.model.lambdasinaction.Dish.menu;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/15 下午1:25
 * @description：
 * @modified By：
 * @version:
 */
public class FindingAndMatching {
    /**
     * anyMatch
     * 方法可以回答“流中是否有一个元素能匹配给定的谓词”
     * @return
     */
    public static boolean isVegetarianFriendlyMenu(){
        return menu.stream().anyMatch(Dish::isVegetarian);
    }

    /**
     * allMatch
     * 方法的工作原理和anyMatch类似,但它会看看流中的元素是否都能匹配给定的谓
     * 词。比如,你可以用它来看看菜品是否有利健康(即所有菜的热量都低于1000卡路里):
     * @return
     */
    public static boolean isHealthyMenu(){
        return menu.stream().allMatch(d -> d.getCalories() < 1000);
    }

    /**
     * noneMatch
     * 和allMatch相对的是noneMatch 。它可以确保流中没有任何元素与给定的谓词匹配
     * @return
     */
    public static boolean isHealthyMenu2(){
        return menu.stream().noneMatch(d -> d.getCalories() >= 1000);
    }

    /**
     * findAny
     * 方法将返回当前流中的任意元素
     * @return
     */
    public static Optional<Dish> findVegetarianDish(){
        return menu.stream().filter(Dish::isVegetarian).findAny();
    }

    /**
     * 何时使用findFirst和findAny
     * 你可能会想,为什么会同时有findFirst和findAny呢?答案是并行。找到第一个元素
     * 在并行上限制更多。如果你不关心返回的元素是哪个,请使用findAny,因为它在使用并行流
     * 时限制较少。
     */
    public static void findFirst() {
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> firstSquareDivisibleByThree = someNumbers.stream()
                        .map(x -> x * x)
                        .filter(x -> x % 3 == 0)
                        .findFirst(); // 9

    }
}
