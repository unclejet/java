package com.uj.study.design.patterns.chain.responsibility;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/30 下午12:55
 * @description：
 */
public class HeaderTextProcessing extends ProcessingObject<String> {
    public String handleWork(String text) {
        return "From Raoul, Mario and Alan: " + text;
    }
}
