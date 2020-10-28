package com.uj.study.stream;

import com.uj.study.model.lambdasinaction.Album;
import com.uj.study.model.lambdasinaction.Artist;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/12 下午1:25
 * @description：
 * @modified By：
 * @version:
 */
public class BadExample {
    private Album album;

    /**
     * 误用 Stream 的例子
     */
    public void bad() {
        List<Artist> musicians = album.getMusicians()
                .collect(toList());
        List<Artist> bands = musicians.stream()
                .filter(artist -> artist.getName().startsWith("The"))
                .collect(toList());
        Set<String> origins = bands.stream()
                .map(artist -> artist.getNationality())
                .collect(toSet());
    }

    /**
     * 符合 Stream 使用习惯的链式调用
     */
    public void good() {
        Set<String> origins = album.getMusicians()
                .filter(artist -> artist.getName().startsWith("The"))
                .map(artist -> artist.getNationality())
                .collect(toSet());

    }
}
