package com.uj.study.debug.test.refactoring;

import com.uj.study.functional.Logger;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/2 下午12:12
 * @description：
 */
public class LogRefactoring {
    /**
     * logger 对象使用 isDebugEnabled 属性避免不必要的性能开销
     */
    public static void f1() {
        Logger logger = new Logger();
        if (logger.isDebugEnabled()) {
            logger.debug("Look at this: " + expensiveOperation());
        }
    }

    private static String expensiveOperation() {
        //...
        return null;
    }

    /**
     *上述反模式通过传入代码即数据的方式很容易解决。与其查询并设置一个对象的值,不如
     * 传入一个 Lambda 表达式,该表达式按照计算得出的值执行相应的行为。我将原来的实现
     * 代码列在例 7-2 中,以示提醒。当程序处于调试级别,并且检查是否使用 Lambda 表达式
     * 的逻辑被封装在 Logger 对象中时,才会调用 Lambda 表达式。
     */
    public static void f2() {
        Logger logger = new Logger();
        logger.debug(() -> "Look at this: " + expensiveOperation());
    }
}
