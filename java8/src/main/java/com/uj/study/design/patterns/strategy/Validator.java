package com.uj.study.design.patterns.strategy;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/24 下午12:49
 * @description：
 */
public class Validator {
    private final ValidationStrategy strategy;

    public Validator(ValidationStrategy v) {
        this.strategy = v;
    }

    public boolean validate(String s) {
        return strategy.execute(s);
    }
}
