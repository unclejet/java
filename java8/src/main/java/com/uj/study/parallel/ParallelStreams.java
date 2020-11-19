package com.uj.study.parallel;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author ：unclejet
 * @date ：Created in 2020/11/13 下午1:24
 * @description：
 * 配置并行流使用的线程池
 * 看看流的parallel方法,你可能会想,并行流用的线程是从哪儿来的?有多少个?怎么
 * 自定义这个过程呢?
并行流内部使用了默认的ForkJoinPool (7.2节会进一步讲到分支/合并框架),它默认的
线 程 数 量 就 是 你 的 处 理 器 数 量 , 这 个 值 是 由 Runtime.getRuntime().available-
Processors()得到的。
但 是 你 可 以 通 过 系 统 属 性 java.util.concurrent.ForkJoinPool.common.
parallelism来改变线程池大小,如下所示:
System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","12");
这是一个全局设置,因此它将影响代码中所有的并行流。反过来说,目前还无法专为某个
并行流指定这个值。一般而言,让ForkJoinPool的大小等于处理器数量是个不错的默认值,
除非你有很好的理由,否则我们强烈建议你不要修改它。

 */
public class ParallelStreams {
    /**
     * 传统做法
     * 接受数字n作为参数,并返回从1到给定参数的所有数字的和
     * @param n
     * @return
     */
    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 0; i <= n; i++) {
            result += i;
        }
        return result;
    }

    /**
     * 上题的顺序流
     * @param n
     * @return
     */
    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).reduce(Long::sum).get();
    }

    /**
     * 上题的并行流
     * 这相当令人失望,求和方法的并行版本比顺序版本要慢很多。你如何解释这个意外的结果
     * 呢?这里实际上有两个问题:
     *  iterate生成的是装箱的对象,必须拆箱成数字才能求和;
     *  我们很难把iterate分成多个独立块来并行执行。
     * @param n
     * @return
     */
    public static long parallelSumSlowly(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n)
                .parallel()
                .reduce(Long::sum).get();
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(0L, Long::sum);
    }


    public static long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + sum);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }

    public static void main(String[] args) {
        //108 msecs
//        System.out.println("Sequential sum done in:" +
//                measureSumPerf(ParallelStreams::sequentialSum, 10_000_000) + " msecs");

        //373 msecs
//        System.out.println("Iterative sum done in:" +
//                measureSumPerf(ParallelStreams::parallelSumSlowly, 10_000_000) + " msecs");

        //1 msecs
        System.out.println("Parallel range sum done in:" +
                measureSumPerf(ParallelStreams::parallelRangedSum, 10_000_000) +
                " msecs");

    }

}
