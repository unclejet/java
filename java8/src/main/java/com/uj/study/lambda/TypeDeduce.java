package com.uj.study.lambda;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

/**
 * @author ：unclejet
 * @date ：Created in 2020/9/30 下午1:06
 * @description：
 * @modified By：
 * @version:
 */
public class TypeDeduce {
    /**
     * 使用菱形操作符,根据变量类型做推断
     */
    public void f1() {
        Map<String, Integer> oldWordCounts = new HashMap<String, Integer>();
        //菱形操作符。不用明确声明泛型类型,编译器就可以自己推断出来,这就是它的神奇之处!
        Map<String, Integer> diamondWordCounts = new HashMap<>();

        useHashmap(new HashMap<>());
    }

    private void useHashmap(Map<String, String> values) {}

    /**
     * 类型推断
     */
    public void f2() {
        Predicate<Integer> atLeast5 = x -> x > 5;
    }

    /**
     * Predicate 接口的源码,接受一个对象,返回一个布尔值
     * @param <T>
     */
    public interface Predicate<T> {
        boolean test(T t);
    }

    /**
     * 略显复杂的类型推断
     */
    public void f3() {
        BinaryOperator<Long> addLongs = (x, y) -> x + y;

        //没有泛型,代码则通不过编译
//        BinaryOperator add = (x, y) -> x + y;

    }

}
