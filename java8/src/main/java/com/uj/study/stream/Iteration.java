package com.uj.study.stream;

import com.uj.study.model.lambdasinaction.Artist;

import java.util.Iterator;
import java.util.List;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/9 下午1:23
 * @description：
 * @modified By：
 * @version:
 */
public class Iteration {
    private List<Artist> allArtists;

    /**
     * 使用 for 循环计算来自伦敦的艺术家人数
     */
    public void f1() {
        int count = 0;
        for (Artist artist : allArtists) {
            if (artist.isFrom("London")) {
                count++;
            }
        }
    }

    /**
     * 使用迭代器计算来自伦敦的艺术家人数
     */
    public void f2() {
        int count = 0;
        Iterator<Artist> iterator = allArtists.iterator();
        while(iterator.hasNext()) {
            Artist artist = iterator.next();
            if (artist.isFrom("London")) {
                count++;
            }
        }
    }

    /**
     * 使用内部迭代计算来自伦敦的艺术家人数
     */
    public void f3() {
        long count = allArtists.stream()
                .filter(artist -> artist.isFrom("London"))
                .count();
    }

    /**
     * 只过滤,不计数
     */
    public void f4() {
        allArtists.stream()
                .filter(artist -> artist.isFrom("London"));
    }

    /**
     * 由于使用了惰性求值,没有输出艺术家的名字
     * 如果在过滤器中加入一条 println 语句,来输出艺术家的名字,就能轻而易举地看出其中的不
     * 同。运行这段代码,程序不会输出任何信息!
     */
    public void f5() {
        allArtists.stream()
                .filter(artist -> {
                    System.out.println(artist.getName());
                    return artist.isFrom("London");
                });

    }

    /**
     * 输出艺术家的名字
     * 如果将同样的输出语句加入一个拥有终止操作的流,艺术家的名
     * 字就会被输出
     */
    public void f6() {
        long count = allArtists.stream()
                .filter(artist -> {
                    System.out.println(artist.getName());
                    return artist.isFrom("London");
                })
                .count();

    }

    /**
     * Question 2. 迭代。修改如下代码,将外部迭代转换成内部迭代:
     * int totalMembers = 0;
     * for (Artist artist : artists) {
     *  Stream<Artist> members = artist.getMembers();
     *  totalMembers += members.count();
     * }
     * @param artists
     * @return
     */
    public static int countBandMembersInternal(List<Artist> artists) {
        // NB: readers haven't learnt about primitives yet, so can't use the sum() method
        return artists.stream()
                .map(artist -> artist.getMembers().count())
                .reduce(0L, Long::sum)
                .intValue();

        //return (int) artists.stream().flatMap(artist -> artist.getMembers()).count();
    }
}
