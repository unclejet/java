package com.uj.study.parallel.future;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/18 上午11:50
 * @description：
 */
public class AlbumLookupException extends RuntimeException {

    public AlbumLookupException(Throwable cause) {
        super(cause);
    }

    public AlbumLookupException(String message) {
        super(message);
    }

}

