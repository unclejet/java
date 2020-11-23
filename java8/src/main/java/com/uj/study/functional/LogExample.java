package com.uj.study.functional;

import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/23 上午10:45
 * @description：
 */
public class LogExample {
    private Logger logger;

    /**
     * bad
     * 这段代码有什么问题吗?其实问题不少。
     *  日志器的状态(它支持哪些日志等级)通过isLoggable方法暴露给了客户端代码。
     *  为什么要在每次输出一条日志之前都去查询日志器对象的状态?这只能搞砸你的代码。
     * 更好的方案是使用log方法,该方法在输出日志消息之前,会在内部检查日志对象是否已经
     * 设置为恰当的日志等级:
     */
    public void logBad() {
        if (logger.isLoggable(Level.FINER)){
            logger.finer("Problem: " + generateDiagnostic());
        }
    }

    /**
     * JDK logger used Supplier already.
     */
    public void logBetter() {
        logger.log(Level.FINER, () -> "Problem: " + generateDiagnostic());
    }

    /**
     * 这种方式更好的原因是你不再需要在代码中插入那些条件判断,与此同时日志器的状态也不
     * 再被暴露出去。
     * @param level
     * @param msgSupplier
     */
    public void log(Level level, Supplier<String> msgSupplier){
        if(logger.isLoggable(level)){
            log(level, msgSupplier.get());
        }
    }

    private void log(Level level, String msg) {
        logger.finer("Problem: " + generateDiagnostic());
    }



    private String generateDiagnostic() {
        return "some stack trace print";
    }
}


