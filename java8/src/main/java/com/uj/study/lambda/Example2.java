package com.uj.study.lambda;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.BinaryOperator;

/**
 * @author ：unclejet
 * @date ：Created in 2020/9/29 下午1:14
 * @description：
 * @modified By：
 * @version:
 */
public class Example2 {

    private final JButton button = new JButton();

    public static void main(String[] args) {
        System.out.println("main");
    }

    /**
     * 编写 Lambda 表达式的不同形式
     */
    public void diff() {
        Runnable noArguments = () -> System.out.println("Hello World");

        ActionListener oneArgument = event -> System.out.println("button clicked");

        Runnable multiStatement = () -> {
            System.out.print("Hello");
            System.out.println(" World");
        };

        BinaryOperator<Long> add = (x, y) -> x + y;

        BinaryOperator<Long> addExplicit = (Long x, Long y) -> x + y;
    }

    /**
     * 等号右边的代码并没有声明类型,系统根据上下文推断出类型信息
     */
    public void f1() {
        final String[] array = { "hello", "world" };
    }


    /**
     * 匿名内部类中使用 final 局部变量
     */
    public void f2() {
        final String name = getUserName();
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("hi " + name);
            }
        });

    }

    private String getUserName() {
        return "username";
    }

    /**
     * Lambda 表达式中引用既成事实上的 final 变量
     */
    public void f3() {
        String name = getUserName();
        button.addActionListener(event -> System.out.println("hi " + name));
    }

    /**
     * 未使用既成事实上的 final 变量,导致无法通过编译
     */
    public void f4() {
        String name = getUserName();
//        name = formatUserName(name);
        button.addActionListener(event -> System.out.println("hi " + name));
    }

    private String formatUserName(String name) {
        return name;
    }
}
