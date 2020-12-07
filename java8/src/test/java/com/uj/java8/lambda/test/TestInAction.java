package com.uj.java8.lambda.test;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/3 下午12:19
 * @description：
 */
public class TestInAction {
    /**
     * 第一种是将 Lambda 表达式放入一个方法测试,这种方式要测那
     * 个方法,而不是 Lambda 表达式本身。
     * @param words
     * @return
     */
    public static List<String> allToUpperCase(List<String> words) {
        return words.stream()
                .map(string -> string.toUpperCase())
                .collect(Collectors.<String>toList());
    }

    @Test
    public void multipleWordsToUppercase() {
        List<String> input = asList("a", "b", "hello");
        List<String> result = allToUpperCase(input);
        assertEquals(asList("A", "B", "HELLO"), result);
    }

    public static List<String> elementFirstToUpperCaseLambdas(List<String> words) {
        return words.stream()
                .map(value -> {
                    char firstChar = Character.toUpperCase(value.charAt(0));
                    return firstChar + value.substring(1);
                })
                .collect(Collectors.<String>toList());
    }

    @Test
    public void twoLetterStringConvertedToUppercaseLambdas() {
        List<String> input = Arrays.asList("ab");
        List<String> result = elementFirstToUpperCaseLambdas(input);
        assertEquals(asList("Ab"), result);
    }

    /**
     * 第二种方式就是把业务逻辑提炼出来成为方法引用，lambda只是技术实现，不会刻意关注
     * @param words
     * @return
     */
    public static List<String> elementFirstToUppercase(List<String> words) {
        return words.stream()
                .map(TestInAction::firstToUppercase)
                .collect(Collectors.<String>toList());
    }
    public static String firstToUppercase(String value) {
        char firstChar = Character.toUpperCase(value.charAt(0));
        return firstChar + value.substring(1);
    }

    @Test
    public void twoLetterStringConvertedToUppercase() {
        String input = "ab";
        String result = firstToUppercase(input);
        assertEquals("Ab", result);
    }



}
