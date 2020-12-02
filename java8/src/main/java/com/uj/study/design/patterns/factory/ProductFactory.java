package com.uj.study.design.patterns.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/1 下午12:17
 * @description：这是个全新的尝试,它使用Java 8中的新特性达到了传统工厂模式同样的效果。但是,如果
 * 工厂方法createProduct需要接收多个传递给产品构造方法的参数,这种方式的扩展性不是很
 * 好。你不得不提供不同的函数接口,无法采用之前统一使用一个简单接口的方式。
 * 比如,我们假设你希望保存具有三个参数(两个参数为Integer类型,一个参数为String
 * 类型)的构造函数;为了完成这个任务,你需要创建一个特殊的函数接口TriFunction。最终
 * 的结果是Map变得更加复杂。
 * public interface TriFunction<T, U, V, R>{
 * R apply(T t, U u, V v);
 * }
 * Map<String, TriFunction<Integer, Integer, String, Product>> map
 * = new HashMap<>();
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
