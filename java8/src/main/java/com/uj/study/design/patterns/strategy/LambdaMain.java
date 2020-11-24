package com.uj.study.design.patterns.strategy;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/24 下午12:49
 * @description：
 */
public class LambdaMain {
    public static void main(String[] args) {
        // old school
        Validator v1 = new Validator(new IsNumeric());
        System.out.println(v1.validate("aaaa"));
        Validator v2 = new Validator(new IsAllLowerCase ());
        System.out.println(v2.validate("bbbb"));


        // with lambdas
        Validator v3 = new Validator((String s) -> s.matches("\\d+"));
        System.out.println(v3.validate("aaaa"));
        Validator v4 = new Validator((String s) -> s.matches("[a-z]+"));
        System.out.println(v4.validate("bbbb"));
    }
}
