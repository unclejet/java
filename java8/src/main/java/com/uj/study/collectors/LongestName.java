package com.uj.study.collectors;

import com.uj.study.model.lambdasinaction.Artist;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * @author ：unclejet
 * @date ：Created in 2020/11/4 下午1:11
 * @description：
 *  找出名字最长的艺术家, 分别使用收集器和第 3 章介绍过的 reduce 高阶函数实现。
 *  然后对比二者的异同:哪一种方式写起来更简单,哪一种方式读起来更简单?以下面
 * 的参数为例,该方法的正确返回值为 "Stuart Sutcliffe":
 * Stream<String> names = Stream.of("John Lennon", "Paul McCartney",
 * "George Harrison", "Ringo Starr", "Pete Best", "Stuart Sutcliffe");
 * @modified By：
 * @version:
 */
public class LongestName {
    private static Comparator<Artist> byNameLength = comparing(artist -> artist.getName().length());

    public static Artist byReduce(List<Artist> artists) {
        return artists.stream()
                .reduce((acc, artist) -> {
                    return (byNameLength.compare(acc, artist) >= 0) ? acc : artist;
                })
                .orElseThrow(RuntimeException::new);
    }

    public static Artist byCollecting(List<Artist> artists) {
        return artists.stream()
                .collect(Collectors.maxBy(byNameLength))
                .orElseThrow(RuntimeException::new);
    }
}
