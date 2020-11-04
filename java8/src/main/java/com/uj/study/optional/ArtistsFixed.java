package com.uj.study.optional;

import com.uj.study.model.lambdasinaction.Artist;

import java.util.List;
import java.util.Optional;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/31 下午1:23
 * @description：
 * @modified By：
 * @version:
 * 例 4-26 所示的 Artists 类表示了一组艺术家,重构该类,使得 getArtist 方法返回一
 * 个 Optional<Artist> 对象。如果索引在有效范围内,返回对应的元素,否则返回一个空
 * Optional 对象。此外,还需重构 getArtistName 方法,保持相同的行为。
 * 例 4-26
 *  包含多个艺术家的 Artists 类
 * public class Artists {
 * private List<Artist> artists;
 * public Artists(List<Artist> artists) {
 * this.artists = artists;
 * }
 * }
 * public Artist getArtist(int index) {
 * if (index < 0 || index >= artists.size()) {
 * indexException(index);
 * }
 * return artists.get(index);
 * }
 * private void indexException(int index) {
 * throw new IllegalArgumentException(index +
 * "doesn't correspond to an Artist");
 * }
 * public String getArtistName(int index) {
 * try {
 * Artist artist = getArtist(index);
 * return artist.getName();
 * } catch (IllegalArgumentException e) {
 * return "unknown";
 * }
 * }
 */
public class ArtistsFixed {
    private List<Artist> artists;

    public ArtistsFixed(List<Artist> artists) {
        this.artists = artists;
    }

    public Optional<Artist> getArtist(int index) {
        if (index < 0 || index >= artists.size()) {
            return Optional.empty();
        }
        return Optional.of(artists.get(index));
    }

    public String getArtistName(int index) {
        Optional<Artist> artist = getArtist(index);
        return artist.map(Artist::getName)
                .orElse("unknown");
    }
}
