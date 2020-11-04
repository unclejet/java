package com.uj.study.functional;

import com.uj.study.model.lambdasinaction.Artist;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.maxBy;

/**
 * @author ：unclejet
 * @date ：Created in 2020/11/3 下午1:15
 * @description：
 * @modified By：
 * @version:
 */
public class DeclareFunctional {
    public Optional<Artist> biggestGroup(Stream<Artist> artists) {
        Function<Artist,Long> getCount = artist -> artist.getMembers().count();

        return artists.collect(maxBy(comparing(getCount)));
    }
}
