package com.uj.study.debug.test.refactoring;

import com.uj.study.model.lambdasinaction.Album;
import com.uj.study.model.lambdasinaction.Track;

import java.util.List;
import java.util.function.ToLongFunction;


/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/2 下午12:26
 * @description：
 */
public class RepeatRefactoring {
    private static List<Album> albums;

    /**
     * 下面每个方法里,都有样板代码将每个专辑里的属性和总数相加,比如每首曲目的长度或音乐
     * 家的人数。
     * @return
     */
    public long countRunningTime() {
        long count = 0;
        for (Album album : albums) {
            for (Track track : album.getTrackList()) {
                count += track.getLength();
            }
        }
        return count;
    }

    public long countMusicians() {
        long count = 0;
        for (Album album : albums) {
            count += album.getMusicianList().size();
        }
        return count;
    }
    public long countTracks() {
        long count = 0;
        for (Album album : albums) {
            count += album.getTrackList().size();
        }
        return count;
    }

    /**
     * 使用流重构命令式的 Order 类
     * @return
     */
    public long countRunningTime() {
        return albums.stream()
                .mapToLong(album -> album.getTracks()
                        .mapToLong(track -> track.getLength())
                        .sum())
                .sum();
    }
    public long countMusicians() {
        return albums.stream()
                .mapToLong(album -> album.getMusicians().count())
                .sum();
    }
    public long countTracks() {
        return albums.stream()
                .mapToLong(album -> album.getTracks().count())
                .sum();
    }

    /**
     * 使用领域方法(ToLongFunction)重构 Order 类
     * @param function
     * @return
     */
    public long countFeature(ToLongFunction<Album> function) {
        return albums.stream()
                .mapToLong(function)
                .sum();
    }

    public long countTracks() {
        return countFeature(album -> album.getTracks().count());
    }

    public long countRunningTime() {
        return countFeature(album -> album.getTracks()
                .mapToLong(track -> track.getLength())
                .sum());
    }

    public long countMusicians() {
        return countFeature(album -> album.getMusicians().count());
    }

}
