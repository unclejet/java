package com.uj.study.design.patterns.chain.responsibility;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/30 下午12:58
 * @description：
 */
public class Main {
    public static void main(String[] args) {
        //old school
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();
        p1.setSuccessor(p2);
        String result1 = p1.handle("Aren't labdas really sexy?!!");
        System.out.println(result1);

        //lambda
        UnaryOperator<String> headerProcessing =
                (String text) -> "From Raoul, Mario and Alan: " + text;
        UnaryOperator<String> spellCheckerProcessing =
                (String text) -> text.replaceAll("labda", "lambda");
        Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);
        String result2 = pipeline.apply("Aren't labdas really sexy?!!");
        System.out.println(result2);
    }
}
