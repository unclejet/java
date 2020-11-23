package com.uj.study.parallel;

import java.util.stream.IntStream;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/23 上午8:40
 * @description：
 */
public class SerialToParallel {
    public static int sumOfSquares(IntStream range) {
        return range.parallel()
                .map(x -> x * x)
                .sum();
    }

    public static int sequentialSumOfSquares(IntStream range) {
        return range.map(x -> x * x)
                .sum();
    }
}
