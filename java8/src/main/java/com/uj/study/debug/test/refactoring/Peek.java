package com.uj.study.debug.test.refactoring;

import com.uj.study.model.lambdasinaction.Album;
import com.uj.study.model.lambdasinaction.Artist;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/1 下午12:42
 * @description：记录日志这是 peek 方法的用途之一。为了像调试循环那样一步一步跟踪,可在 peek 方法
 * 中加入断点,这样就能逐个调试流中的元素了。
 */
public class Peek {
    public static void main(String[] args) {
        //peek
        List<Integer> result = Stream.of(2, 3, 4, 5)
                .peek(x -> System.out.println("taking from stream: " + x)).map(x -> x + 17)
                .peek(x -> System.out.println("after map: " + x)).filter(x -> x % 2 == 0)
                .peek(x -> System.out.println("after filter: " + x)).limit(3)
                .peek(x -> System.out.println("after limit: " + x)).collect(toList());
    }

    //another example

    //for print
    public static Set<String> imperativeNationalityReport(Album album) {
        // BEGIN imperative_nationality_report
        Set<String> nationalities = new HashSet<>();
        for (Artist artist : album.getMusicianList()) {
            if (artist.getName().startsWith("The")) {
                String nationality = artist.getNationality();
                System.out.println("Found nationality: " + nationality);
                nationalities.add(nationality);
            }
        }
        return nationalities;
        // END imperative_nationality_report
    }

    //foreach print
    public static Set<String> forEachLoggingFailure(Album album) {
        // BEGIN foreach_logging_nationality_report
        album.getMusicians()
                .filter(artist -> artist.getName().startsWith("The"))
                .map(artist -> artist.getNationality())
                .forEach(nationality -> System.out.println("Found: " + nationality));

        Set<String> nationalities
                = album.getMusicians()
                .filter(artist -> artist.getName().startsWith("The"))
                .map(artist -> artist.getNationality())
                .collect(Collectors.<String>toSet());
        // END foreach_logging_nationality_report
        return nationalities;
    }

    //peek
    public static Set<String> nationalityReportUsingPeek(Album album) {
        // BEGIN peek_nationality_report
        Set<String> nationalities
                = album.getMusicians()
                .filter(artist -> artist.getName().startsWith("The"))
                .map(artist -> artist.getNationality())
                .peek(nation -> System.out.println("Found nationality: " + nation))
                .collect(Collectors.<String>toSet());
        // END peek_nationality_report
        return nationalities;
    }
}
