package com.uj.study.design.patterns.factory;

import java.util.function.Supplier;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/1 下午12:21
 * @description：
 */
public class Main {
    public static void main(String[] args) {
        //old school
        Product p1 = ProductFactory.createProduct("loan");

        //not use factory
        Supplier<Product> loanSupplier = Loan::new;
        Product p2 = loanSupplier.get();

        //lambda factory
        Product p3 = ProductFactory.createProductLambda("loan");

    }
}
