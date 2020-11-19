package com.uj.study.parallel;

import com.uj.study.model.lambdasinaction.Album;
import com.uj.study.model.lambdasinaction.SampleData;
import com.uj.study.model.lambdasinaction.Track;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/19 下午6:04
 * @description：
 */
public class ArraySum {
    public List<Album> albums;

    public void initAlbums() {
        int n = Integer.getInteger("arraysum.size", 1000);
        albums = IntStream.range(0, n)
                .mapToObj(i -> SampleData.aLoveSupreme.copy())
                .collect(toList());
    }

    // BEGIN serial
    public int serialArraySum() {
        return albums.stream()
                .flatMap(Album::getTracks)
                .mapToInt(Track::getLength)
                .sum();
    }
    // END serial

    // BEGIN parallel
    public int parallelArraySum() {
        return albums.parallelStream()
                .flatMap(Album::getTracks)
                .mapToInt(Track::getLength)
                .sum();
    }
    // END parallel

}
