package com.uj.study.design.patterns.strategy.example2;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/7 下午12:10
 * @description：
 */
public class ZipCompressionStrategy implements CompressionStrategy {

    @Override
    public OutputStream compress(OutputStream data) throws IOException {
        return new ZipOutputStream(data);
    }
}
