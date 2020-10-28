package com.uj.study.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/13 下午1:33
 * @description：
 * @modified By：
 * @version:
 */
public class Challenge {
    /**
     * 1. 只用 reduce 和 Lambda 表达式写出实现 Stream 上的 map 操作的代码,如果不想返回
     * Stream,可以返回一个 List。
     * I means input
     * O means output
     */
    public static class MapUsingReduce {
        public static <I, O> List<O> map(Stream<I> stream, Function<I, O> mapper) {
            return stream.reduce(new ArrayList<O>(), (acc, x) -> {
                // We are copying data from acc to new list instance. It is very inefficient,
                // but contract of Stream.reduce method requires that accumulator function does
                // not mutate its arguments.
                // Stream.collect method could be used to implement more efficient mutable reduction,
                // but this exercise asks to use reduce method.
                List<O> newAcc = new ArrayList<>(acc);
                newAcc.add(mapper.apply(x));
                return newAcc;
            }, (List<O> left, List<O> right) -> {
                // We are copying left to new list to avoid mutating it.
                List<O> newLeft = new ArrayList<>(left);
                newLeft.addAll(right);
                return newLeft;
            });
        }

    }

    /**
     * 2. 只用 reduce 和 Lambda 表达式写出实现 Stream 上的 filter 操作的代码,如果不想返回
     * Stream,可以返回一个 List。
     * @param stream
     * @param predicate
     * @param <I>
     * @return
     */
    public static <I> List<I> filter(Stream<I> stream, Predicate<I> predicate) {
        List<I> initial = new ArrayList<>();
        return stream.reduce(initial,
                (List<I> acc, I x) -> {
                    if (predicate.test(x)) {
                        // We are copying data from acc to new list instance. It is very inefficient,
                        // but contract of Stream.reduce method requires that accumulator function does
                        // not mutate its arguments.
                        // Stream.collect method could be used to implement more efficient mutable reduction,
                        // but this exercise asks to use reduce method explicitly.
                        List<I> newAcc = new ArrayList<>(acc);
                        newAcc.add(x);
                        return newAcc;
                    } else {
                        return acc;
                    }
                },
                Challenge::combineLists);
    }

    private static <I> List<I> combineLists(List<I> left, List<I> right) {
        // We are copying left to new list to avoid mutating it.
        List<I> newLeft = new ArrayList<>(left);
        newLeft.addAll(right);
        return newLeft;
    }

}
