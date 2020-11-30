package com.uj.study.design.patterns.chain.responsibility;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/30 下午12:56
 * @description：
 */
public class SpellCheckerProcessing extends ProcessingObject<String> {
    public String handleWork(String text) {
        return text.replaceAll("labda", "lambda");
    }
}
