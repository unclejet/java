package com.uj.study.lambda;

import com.uj.study.collectors.Grouping;
import com.uj.study.lambda.entity.Apple;
import com.uj.study.model.lambdasinaction.Album;
import com.uj.study.model.lambdasinaction.Artist;
import com.uj.study.model.lambdasinaction.Dish;
import com.uj.study.model.lambdasinaction.Dish.CaloricLevel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.uj.study.lambda.CollectorExamples.countWords;
import static com.uj.study.model.lambdasinaction.Dish.menu;
import static java.nio.charset.Charset.defaultCharset;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/4 上午10:50
 * @description：
 * @modified By：
 * @version:
 */
public class MethodReference {


    public Map<Artist, List<Album>> albumsByArtist(Stream<Album> albums) {
        return albums.collect(groupingBy(Album::getMainMusician));
    }

    private static final Pattern SPACES = Pattern.compile("\\w+");

    public static Map<String, Long> countWordsIn(Path path) throws IOException {
        Stream<String> words = Files.readAllLines(path, defaultCharset())
                .stream()
                .flatMap(SPACES::splitAsStream);//按w模式分割后就变成了单词流

        return countWords(words);
    }

    /**
     * not good to write many code in lambda
     */
    public static void f1() {
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel =
                menu.stream().collect(
                                groupingBy(dish -> {
                                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                                    else return CaloricLevel.FAT;
                                }));

        //method reference, the same with the above lots of code.
        dishesByCaloricLevel = menu.stream().collect(groupingBy(Dish::getCaloricLevel));
    }

    public static void f2() {
        int totalCalories = menu.stream().map(Dish::getCalories)
                        .reduce(0, (c1, c2) -> c1 + c2);

        //method references
        totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
    }

    public static void f3() {
        List<Apple> inventory = Arrays.asList(new Apple(80,"green"), new Apple(155, "green"), new Apple(120, "red"));
        inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));

        //method reference
        inventory.sort(comparing(Apple::getWeight));
    }
}
