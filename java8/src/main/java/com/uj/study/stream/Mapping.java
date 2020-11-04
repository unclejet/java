package com.uj.study.stream;

import com.uj.study.model.lambdasinaction.Artist;
import com.uj.study.model.lambdasinaction.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/14 下午1:32
 * @description：
 * @modified By：
 * @version:
 */
public class Mapping {
    public static void simpleMap() {
        List<String> dishNames = Dish.menu.stream()
                .map(Dish::getName)
                .collect(toList());
        System.out.println(dishNames);
    }


    public static void simpleMap1() {
        List<String> words = Arrays.asList("Hello", "World");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(toList());
        System.out.println(wordLengths);
    }

    /**
     * 对于一张单词表,如何返回一张列表,列出里面 各不相同的字符 呢?
     * 例如,给定单词列表["Hello","World"],
     * 你想要返回列表["H","e","l", "o","W","r","d"]。
     */
    public static void notWork1() {
        List<String> words = Arrays.asList("Hello", "World");
        words.stream()
                .map(word -> word.split(""))// Stream<String[]>
                .distinct()//Stream<String[]>
                .collect(toList());

    }

    public static void notWork2() {
        List<String> words = Arrays.asList("Hello", "World");
        words.stream()
                .map(word -> word.split(""))// Stream<String[]>
                .map(Arrays::stream) //Stream<Stream<String>>
                .distinct()
                .collect(toList());
    }

    /**
     * 一言以蔽之, flatmap方法让你把一个流中的每个值都换成另一个流,然后把所有的流连接
     * 起来成为一个流。
     */
    public static void flatMap1() {
        List<String> words = Arrays.asList("Hello", "World");
        words.stream()
                .map(word -> word.split(""))// Stream<String[]>
                .flatMap(Arrays::stream) //<Stream<String>>
                .distinct()//Stream<String>
                .collect(toList());
    }

    public static void flatMap2() {
        List<String> words = Arrays.asList("Hello", "World");
        words.stream()
                .flatMap((String line) -> Arrays.stream(line.split("")))
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * CH4 Exercise 1
     * 在例 4-25 所示的 Performance 接口基础上,添加 getAllMusicians 方法,该方法返回包
     * 含所有艺术家名字的 Stream,如果对象是乐队,则返回每个乐队成员的名字。例如,如
     * 果 getMusicians 方法返回甲壳虫乐队,则 getAllMusicians 方法返回乐队名和乐队成员,
     * 如约翰 · 列侬、保罗 · 麦卡特尼等。
     * //该接口表示艺术家的演出——专辑或演唱会
     *public interface Performance {
     *         public String getName();
     *         public Stream<Artist> getMusicians();
     *     }
     */
    public interface PerformanceFixed {

        public String getName();

        public Stream<Artist> getMusicians();

        public default Stream<Artist> getAllMusicians() {
            return getMusicians().flatMap(artist -> concat(Stream.of(artist), artist.getMembers()));
        }

    }
}
