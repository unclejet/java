package com.uj.study.design.patterns.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/1 下午12:17
 * @description：
 */
public class ProductFactory {
    private static Map<String, Supplier<Product>> map = new HashMap<>();
    static {
        map.put("loan", Loan::new);
        map.put("stock", Stock::new);
        map.put("bond", Bond::new);
    }

    //old school
    public static Product createProduct(String name){
        switch(name){
            case "loan": return new Loan();
            case "stock": return new Stock();
            case "bond": return new Bond();
            default: throw new RuntimeException("No such product " + name);
        }
    }

    //lambda
    public static Product createProductLambda(String name){
        Supplier<Product> p = map.get(name);
        if(p != null) return p.get();
        throw new RuntimeException("No such product " + name);
    }
}
