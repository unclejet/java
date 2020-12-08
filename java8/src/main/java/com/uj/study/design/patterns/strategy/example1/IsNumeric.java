package com.uj.study.design.patterns.strategy.example1;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/24 下午12:48
 * @description：
 */
public class IsNumeric implements ValidationStrategy {
    public boolean execute(String s){
        return s.matches("\\d+");
    }
}
