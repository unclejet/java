package com.uj.study.lambda;

import com.uj.study.lambda.entity.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/4 上午11:05
 * @description：
 * 许多函数式接口, 比如用
 * 于传递Lambda表达式的Comparator、 Function和Predicate都提供了允许你进行复合的方法。
 * 这是什么意思呢?在实践中,这意味着你可以把多个简单的Lambda复合成复杂的表达式。比如,
 * 你可以让两个谓词之间做一个or操作,组合成一个更大的谓词。而且,你还可以让一个函数的结
 * 果成为另一个函数的输入。
 * @modified By：
 * @version:
 */
public class ComposeLambdaExpressions {
    List<Apple> inventory = new ArrayList<>();

    public ComposeLambdaExpressions() {
        inventory.addAll(Arrays.asList(new Apple(80,"green"), new Apple(155, "green"), new Apple(120, "red")));
    }

    /**
     * 比较器复合
     * 倒序
     */
    public void composingComparators() {
        Comparator<Apple> c = comparing(Apple::getWeight);
        //Reversed order
        inventory.sort(comparing(Apple::getWeight).reversed());

        //Chaining Comparators
        inventory.sort(comparing(Apple::getWeight)
                .reversed() //按重量递减排序
                .thenComparing(Apple::getCountry)); //两个苹果一样重时,进一步按国家排序
    }

    /**
     * 谓词复合
     * 谓词接口包括三个方法:negate、and和or,让你可以重用已有的Predicate来创建更复
     * 杂的谓词。比如,你可以使用negate方法来返回一个 Predicate的非,比如苹果不是红的
     */
    public void composingPredicates() {
        Predicate<Apple> redApple = apple -> apple.getColor().equals("red");
        //产 生 现 有 Predicate对象redApple的非
        Predicate<Apple> notRedApple = redApple.negate();

        //链接两个谓词来生成另一个Predicate对象
        Predicate<Apple> redAndHeavyApple = redApple.and(a -> a.getWeight() > 150);

        //链接Predicate的方法来构造更复杂Predicate对象
        Predicate<Apple> redAndHeavyAppleOrGreen =
        redApple.and(a -> a.getWeight() > 150)
                .or(a -> "green".equals(a.getColor()));
    }

    /**
     *  函数复合
     *  你还可以把Function接口所代表的Lambda表达式复合起来。Function接口为此配
     * 了andThen和compose两个默认方法,它们都会返回Function的一个实例。
     */
    public void composingFunctions() {
        //andThen
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.andThen(g);
        int result = h.apply(1); //这将返回4

        //compose
        Function<Integer, Integer> f1 = x -> x + 1;
        Function<Integer, Integer> g1 = x -> x * 2;
        Function<Integer, Integer> h1 = f1.compose(g1);
        int result1 = h1.apply(1);//这将返回3
    }

    public static class Letter{
        public static String addHeader(String text){
            return "From Raoul, Mario and Alan: " + text;
        }
        public static String addFooter(String text){
            return text + " Kind regards";
        }
        public static String checkSpelling(String text){
            return text.replaceAll("labda", "lambda");
        }

        public void lineStream() {
            Function<String, String> addHeader = Letter::addHeader;
            Function<String, String> transformationPipeline
                    = addHeader.andThen(Letter::checkSpelling)
                    .andThen(Letter::addFooter);
        }
    }

}
