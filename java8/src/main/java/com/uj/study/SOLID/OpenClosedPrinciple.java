package com.uj.study.SOLID;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/15 上午11:48
 * @description：
 */
public class OpenClosedPrinciple {
    //example 1

    /**
     * 设计 MetricDataGraph 类的方法之一是将代理收集到的各项指标放入该类
     * 但这样的设计意味着每次想往散点图中添加新的时间点,都要修改 MetricDataGraph 类,增加新的XXXTime方法
     */
    abstract class MetricDataGraph {
        public abstract void updateUserTime(int value);

        public abstract void updateSystemTime(int value);

        public abstract void updateIoTime(int value);
    }

    /**
     * 每项具体指标现在可以实现 TimeSeries 接口,在需要时能直接插入。比如,我们可能会
     * 有 如 下 类:UserTimeSeries、SystemTimeSeries 和 IoTimeSeries。 如 果 要 添 加 新 的, 比
     * 如 由 于 虚 拟 化 所 浪 费 的 CPU 时 间, 则 可 增 加 一 个 新 的 实 现 了 TimeSeries 接 口 的 类:
     * StealTimeSeries。这样,就扩展了 MetricDataGraph 类,但并没有修改它。
     */
    abstract class MetricDataGraphOpenClosed {
        public abstract void addTimeSeries(TimeSeries values);
    }


    //example 2

    /**
     * 高阶函数也展示出了同样的特性:对扩展开放,对修改闭合。前面提到的 ThreadLocal 类
     * 就是一个很好的例子。ThreadLocal 有一个特殊的变量,每个线程都有一个该变量的副本
     * 并与之交互。该类的静态方法 withInitial 是一个高阶函数,传入一个负责生成初始值的
     * Lambda 表达式。
     * 这符合开闭原则,因为不用修改 ThreadLocal 类,就能得到新的行为。给 withInitial 方
     * 法传入不同的工厂方法,就能得到拥有不同行为的 ThreadLocal 实例。
     * 下面就是两种使用方式
     */
    public void asHigherOrderFunctions() {
// BEGIN local_formatter
// One implementation
        ThreadLocal<DateFormat> localFormatter
                = ThreadLocal.withInitial(() -> new SimpleDateFormat());

// Usage
        DateFormat formatter = localFormatter.get();
// END local_formatter


// BEGIN local_thread_id
// Or...
        AtomicInteger threadId = new AtomicInteger();
        ThreadLocal<Integer> localId
                = ThreadLocal.withInitial(() -> threadId.getAndIncrement());

// Usage
        int idForThisThread = localId.get();
// END local_thread_id
    }
}
