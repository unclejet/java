package com.uj.study.lambda;

import com.uj.study.model.lambdasinaction.Album;
import com.uj.study.model.lambdasinaction.Artist;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.uj.study.lambda.CollectorExamples.countWords;
import static java.nio.charset.Charset.defaultCharset;
import static java.util.stream.Collectors.groupingBy;

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
}
