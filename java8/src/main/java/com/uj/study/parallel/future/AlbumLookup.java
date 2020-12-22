package com.uj.study.parallel.future;

import com.uj.study.model.lambdasinaction.Album;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/18 上午11:41
 * @description：
 */
public interface AlbumLookup {
    Album lookupByName(String albumName);
}
