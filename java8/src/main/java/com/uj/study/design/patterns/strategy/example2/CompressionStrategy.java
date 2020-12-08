package com.uj.study.design.patterns.strategy.example2;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/7 下午12:09
 * @description：
 */
public interface CompressionStrategy {
    OutputStream compress(OutputStream data) throws IOException;
}
