package com.uj.study.dsl;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/11 上午11:55
 * @description：
 */
public class Expect {
    public BoundExpectation that(Object value) {
        return new BoundExpectation(value);
    }
}
