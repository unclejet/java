package com.uj.study.collectors;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：unclejet
 * @date ：Created in 2020/11/4 下午1:21
 * @description：
 * 使用 Map 的 computeIfAbsent 方法高效计算斐波那契数列。这里的“高效”是指避免将那
 * 些较小的序列重复计算多次。
 * @modified By：
 * @version:
 */
public class Fibonacci {
    private final Map<Integer,Long> cache;

    public Fibonacci() {
        cache = new HashMap<>();
        cache.put(0, 0L);
        cache.put(1, 1L);
    }

    public long fibonacci(int x) {
        return cache.computeIfAbsent(x, n -> fibonacci(n-1) + fibonacci(n-2));
    }

    public static void main(String[] args) {
        System.out.println(new Fibonacci().fibonacci(3));
    }
}
