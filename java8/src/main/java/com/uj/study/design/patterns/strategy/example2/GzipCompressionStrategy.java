package com.uj.study.design.patterns.strategy.example2;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/7 下午12:09
 * @description：
 */
public class GzipCompressionStrategy implements CompressionStrategy {

    @Override
    public OutputStream compress(OutputStream data) throws IOException {
        return new GZIPOutputStream(data);
    }
}
