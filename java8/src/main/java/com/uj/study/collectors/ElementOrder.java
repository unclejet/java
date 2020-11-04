package com.uj.study.collectors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

/**
 * @author ：unclejet
 * @date ：Created in 2020/11/2 下午1:28
 * @description：
 * 元素顺序
 * @modified By：
 * @version:
 */
public class ElementOrder {
    /**
     * 在一个有序集合（numbers）中创建一个流时,流中的元素就按出现顺序排列
     */
    public static void f1() {
        List<Integer> numbers = asList(1, 2, 3, 4);
        List<Integer> sameOrder = numbers.stream()
                .collect(toList());
//        assertEquals(numbers, sameOrder);
    }

    /**
     * 如果集合本身就是无序的,由此生成的流也是无序的
     */
    public static void f2() {
        Set<Integer> numbers = new HashSet<>(asList(4, 3, 2, 1));
        List<Integer> sameOrder = numbers.stream()
                .collect(toList());
// 该断言有时会失败
//        assertEquals(asList(4, 3, 2, 1), sameOrder);
    }

    /**
     * 有些集合本身是无序的,但这些操作有时会产生顺序
     */
    public static void f3() {
        Set<Integer> numbers = new HashSet<>(asList(4, 3, 2, 1));
        List<Integer> sameOrder = numbers.stream()
                .sorted()
                .collect(toList());
//        assertEquals(asList(1, 2, 3, 4), sameOrder);
    }
}
