package com.uj.study.stream;

import com.uj.study.model.lambdasinaction.Album;
import com.uj.study.model.lambdasinaction.Artist;
import com.uj.study.model.lambdasinaction.Track;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import static java.lang.Character.isDigit;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/9 下午1:33
 * @description：
 * @modified By：
 * @version:
 */
public class CommonStreamOperation {
    private Album album;

    /**
     * collect(toList())
     */
    public void collect() {
        List<String> collected = Stream.of("a", "b", "c").collect(toList());
    }

    /**
     * map
     * 使用 for 循环将字符串转换为大写
     */
    public void f1() {
        List<String> collected = new ArrayList<>();
        for (String string : asList("a", "b", "hello")) {
            String uppercaseString = string.toUpperCase();
            collected.add(uppercaseString);
        }
    }

    /**
     * map
     * 使用 map 操作将字符串转换为大写形式
     */
    public void f2() {
        List<String> collected = Stream.of("a", "b", "hello")
                .map(string -> string.toUpperCase())
                .collect(toList());
    }

    /**
     * filter
     * 使用循环遍历列表,使用条件语句做判断
     */
    public void f3() {
        List<String> beginningWithNumbers = new ArrayList<>();
        for (String value : asList("a", "1abc", "abc1")) {
            if (isDigit(value.charAt(0))) {
                beginningWithNumbers.add(value);
            }
        }
    }

    /**
     * filter
     */
    public void f4() {
        List<String> beginningWithNumbers
                = Stream.of("a", "1abc", "abc1")
                .filter(value -> isDigit(value.charAt(0)))
                .collect(toList());
    }

    /**
     * flatmap
     * 前面已介绍过 map 操作,它可用一个新的值代替 Stream 中的值。但有时,用户希望让 map
     * 操作有点变化,生成一个新的 Stream 对象取而代之。用户通常不希望结果是一连串的流,
     * 此时 flatMap 最能派上用场。
     */
    public void f5() {
        List<Integer> together = Stream.of(asList(1, 2), asList(3, 4))
                .flatMap(numbers -> numbers.stream())
                .collect(toList());
        // assertEquals(asList(1, 2, 3, 4), together);
    }

    /**
     * max min
     * 使用 Stream 查找最短曲目
     */
    public void f6() {
        List<Track> tracks = asList(new Track("Bakai", 524),
                new Track("Violets for Your Furs", 378),
                new Track("Time Was", 451));
        Track shortestTrack = tracks.stream()
                .min(Comparator.comparing(track -> track.getLength())).get();
//        assertEquals(tracks.get(1), shortestTrack);
    }

    /**
     * max min
     * 使用 for 循环查找最短曲目
     */
    public void f7() {
        List<Track> tracks = asList(new Track("Bakai", 524),
                new Track("Violets for Your Furs", 378),
                new Track("Time Was", 451));
        Track shortestTrack = tracks.get(0);
        for (Track track : tracks) {
            if (track.getLength() < shortestTrack.getLength()) {
                shortestTrack = track;
            }
        }
//        assertEquals(tracks.get(1), shortestTrack);
    }

    /**
     * reduce 模式
     * 首先赋给 accumulator 一个初始值:initialValue,然后在循环体中,通过调用 combine 函
     * 数,拿 accumulator 和集合中的每一个元素做运算,再将运算结果赋给 accumulator,最后
     * accumulator 的值就是想要的结果。
     * 这个模式中的两个可变项是 initialValue 初始值和 combine 函数。在例 3-14 中,我们选列
     * 表中的第一个元素为初始值,但也不必需如此。为了找出最短曲目,combine 函数返回当
     * 前元素和 accumulator 中较短的那个。
     */
    public void f8() {
//        Object accumulator = initialValue;
//        for(Object element : collection) {
//            accumulator = combine(accumulator, element);
//        }
    }

    /**
     * 使用 reduce 求和
     */
    public void f9() {
        int count = Stream.of(1, 2, 3)
                .reduce(0, (acc, element) -> acc + element);
    }

    /**
     * 展开 reduce 操作
     */
    public void f10() {
        BinaryOperator<Integer> accumulator = (acc, element) -> acc + element;
        int count = accumulator.apply(accumulator.apply(
                        accumulator.apply(0, 1),
                        2),
                3);
    }

    /**
     * 整合操作
     */
    public void f11() {
        Set<String> origins = album.getMusicians()
                .filter(artist -> artist.getName().startsWith("The"))
                .map(artist -> artist.getNationality())
                .collect(toSet());
    }

    /**
     * Question 1常用流操作。实现如下函数:
     * a. 编 写 一 个 求 和 函 数, 计 算 流 中 所 有 数 之 和。 例 如,int addUp(Stream<Integer>
     * numbers);
     * @param numbers
     * @return
     */
    public static int addUp(Stream<Integer> numbers) {
        return numbers.reduce(0, (acc, x) -> acc + x);
    }

    /**
     * Question 1常用流操作。实现如下函数:
     * b. 编写一个函数,接受艺术家列表作为参数,返回一个字符串列表,其中包含艺术家的
     * 姓名和国籍;
     * @param artists
     * @return
     */
    public static List<String> getNamesAndOrigins(List<Artist> artists) {
        return artists.stream()
                .flatMap(artist -> Stream.of(artist.getName(), artist.getNationality()))
                .collect(toList());
    }

    /**
     * Question 1常用流操作。实现如下函数:
     * c. 编写一个函数,接受专辑列表作为参数,返回一个由最多包含 3 首歌曲的专辑组成的
     * 列表。
     * @param input
     * @return
     */
    public static List<Album> getAlbumsWithAtMostThreeTracks(List<Album> input) {
        return input.stream()
                .filter(album -> album.getTrackList().size() <= 3)
                .collect(toList());
    }

    /**
     * Question 6
     *  计算一个字符串中小写字母的个数(提示:参阅 String 对象的 chars 方法)。
     * @param string
     * @return
     */
    public static int countLowercaseLetters(String string) {
        return (int) string.chars()
                .filter(Character::isLowerCase)
                .count();
    }

    /**
     * Question 7
     * 在一个字符串列表中,找出包含最多小写字母的字符串。对于空列表,返回 Optional
     * <String> 对象。
     * @param strings
     * @return
     */
    public static Optional<String> mostLowercaseString(List<String> strings) {
        return strings.stream()
                .max(Comparator.comparing(CommonStreamOperation::countLowercaseLetters));
    }
}
