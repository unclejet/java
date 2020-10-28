package com.uj.study.stream;

import com.uj.study.model.lambdasinaction.Album;
import com.uj.study.model.lambdasinaction.Track;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/12 下午1:11
 * @description：
 * @modified By：
 * @version:
 */
public class Refactoring {
    /**
     * 遗留代码:找出长度大于 1 分钟的曲目
     *
     * @param albums
     * @return
     */
    public Set<String> findLongTracks(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        for (Album album : albums) {
            for (Track track : album.getTrackList()) {
                if (track.getLength() > 60) {
                    String name = track.getName();
                    trackNames.add(name);
                }
            }
        }
        return trackNames;
    }

    /**
     * 重构的第一步:找出长度大于 1 分钟的曲目
     * 第一步要修改的是 for 循环。首先使用 Stream 的 forEach 方法替换掉 for 循环,但还是暂
     * 时保留原来循环体中的代码,这是在重构时非常方便的一个技巧。调用 stream 方法从专辑
     * 列表中生成第一个 Stream,同时不要忘了在上一节已介绍过,getTracks 方法本身就返回
     * 一个 Stream 对象。
     *
     * @param albums
     * @return
     */
    public Set<String> findLongTracksStep1(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        albums.stream()
                .forEach(album -> {
                    album.getTracks()
                            .forEach(track -> {
                                if (track.getLength() > 60) {
                                    String name = track.getName();
                                    trackNames.add(name);
                                }
                            });
                });
        return trackNames;
    }

    /**
     * 重构的第二步:找出长度大于 1 分钟的曲目
     * 引入一些更符合流风格的代码
     * 最内层的 forEach 方法正是主要突破口
     *
     * @param albums
     * @return
     */
    public Set<String> findLongTracksStep2(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        albums.stream()
                .forEach(album -> {
                    album.getTracks()
                            .filter(track -> track.getLength() > 60)
                            .map(track -> track.getName())
                            .forEach(name -> trackNames.add(name));
                });
        return trackNames;
    }

    /**
     * 重构的第三步:找出长度大于 1 分钟的曲目
     *
     * @param albums
     * @return
     */
    public Set<String> findLongTracksStep3(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        albums.stream()
                .flatMap(album -> album.getTracks())
                .filter(track -> track.getLength() > 60)
                .map(track -> track.getName())
                .forEach(name -> trackNames.add(name));
        return trackNames;
    }

    /**
     * 重构的第四步:找出长度大于 1 分钟的曲目
     * @param albums
     * @return
     */
    public Set<String> findLongTracksStep4(List<Album> albums) {
        return albums.stream()
                .flatMap(album -> album.getTracks())
                .filter(track -> track.getLength() > 60)
                .map(track -> track.getName())
                .collect(toSet());

    }

}
