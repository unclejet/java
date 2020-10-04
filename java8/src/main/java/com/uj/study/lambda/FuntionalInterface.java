package com.uj.study.lambda;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;

/**
 * @author ：unclejet
 * @date ：Created in 2020/9/29 下午1:30
 * @description： 函数接口是只有一个抽象方法的接口, 用作 Lambda 表达式的类型。
 * @modified By：
 * @version:
 */
public class FuntionalInterface {
    /**
     * ActionListener 接口:接受 ActionEvent 类型的参数,返回空
     */
    @FunctionalInterface
    public interface ActionListener extends EventListener {
        public void actionPerformed(ActionEvent event);
    }

    @FunctionalInterface
    public interface Predicate<T> {
        boolean test(T t);
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        for (T s : list) {
            if (p.test(s)) {
                results.add(s);
            }
        }
        return results;
    }

    /**
     * 使用Predicate
     */
    public void usePredicate() {
        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
        List<String> nonEmpty = filter(Arrays.asList("a", "b", ""), nonEmptyStringPredicate);
    }

    @FunctionalInterface
    public interface Consumer<T> {
        void accept(T t);
    }

    public static <T> void forEach(List<T> list, Consumer<T> c) {
        for (T i : list) {
            c.accept(i);
        }
    }

    /**
     * 使用Consumer
     */
    public void useConsumer() {
        forEach(
                Arrays.asList(1, 2, 3, 4, 5),
                (Integer i) -> System.out.println(i)
        );

    }

    @FunctionalInterface
    public interface Function<T, R>{
        R apply(T t);
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for(T s: list){
            result.add(f.apply(s));
        }
        return result;
    }

    /**
     * 使用Function
     * [7, 2, 6]
     */
    public void useFunction() {
        List<Integer> l = map(Arrays.asList("lambdas","in","action"),
                (String s) -> s.length());
    }
}
