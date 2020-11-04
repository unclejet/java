package com.uj.study.collectors;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * @author ：unclejet
 * @date ：Created in 2020/11/4 下午1:16
 * @description：
 * 假设一个元素为单词的流, 计算每个单词出现的次数。假设输入如下,则返回值为一
 * 个形如 [John → 3, Paul → 2, George → 1] 的 Map:
 * Stream<String> names = Stream.of("John", "Paul", "George", "John",
 * "Paul", "John");
 * @modified By：
 * @version:
 */
public class WordCount {
    public static Map<String, Long> countWords(Stream<String> names) {
        return names.collect(groupingBy(name -> name, counting()));
    }
}
