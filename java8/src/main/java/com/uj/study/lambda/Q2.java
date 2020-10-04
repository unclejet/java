package com.uj.study.lambda;

import javax.swing.text.DateFormatter;

/**
 * @author ：unclejet
 * @date ：Created in 2020/9/30 下午1:26
 * @description：
 * ThreadLocal Lambda 表达式。Java 有一个 ThreadLocal 类,作为容器保存了当前线程里
 * 局部变量的值。Java 8 为该类新加了一个工厂方法,接受一个 Lambda 表达式,并产生
 * 一个新的 ThreadLocal 对象,而不用使用继承,语法上更加简洁。
 * a. 在 Javadoc 或集成开发环境(IDE)里找出该方法。
 * b. DateFormatter 类是非线程安全的。使用构造函数创建一个线程安全的 DateFormatter
 * 对象,并输出日期,如“01-Jan-1970”。
 * @modified By：
 * @version:
 */
public class Q2 {
    public static ThreadLocal<DateFormatter> formatter = Exercises.replaceThisWithSolution();
}
