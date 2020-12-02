package com.uj.study.debug.test.refactoring;

import com.uj.study.model.lambdasinaction.Album;

import java.sql.DatabaseMetaData;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/2 下午12:18
 * @description：
 */
public class ThreadLocalRefactoring {
    private Database database;

    /**
     * 这个代码异味是使用继承,其目的只是为了覆盖一个方法。ThreadLocal 就是一个很好的
     * 例子。ThreadLocal 能创建一个工厂,为每个线程最多只产生一个值。这是确保非线程安
     * 全的类在并发环境下安全使用的一种简单方式。假设要在数据库中查询一个艺术家,但希
     * 望每个线程只做一次这种查询
     */
    public void f1() {
        ThreadLocal<Album> thisAlbum = new ThreadLocal<Album> () {
            @Override protected Album initialValue() {
                return database.lookupCurrentAlbum();
            }
        };
    }

    public void lambda() {
        ThreadLocal<Album> thisAlbum
                = ThreadLocal.withInitial(() -> database.lookupCurrentAlbum());
    }

    private class Database {
        public Album lookupCurrentAlbum() {
            //...
            return null;
        }
    }
}
