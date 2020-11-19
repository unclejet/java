package com.uj.study.parallel;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/17 下午1:46
 * @description：
 * 分支/合并框架的目的是以递归方式将可以并行的任务拆分成更小的任务,然后将每个子任
 * 务的结果合并起来生成整体结果。它是ExecutorService接口的一个实现,它把子任务分配给
 * 线程池(称为ForkJoinPool)中的工作线程。首先来看看如何定义任务和子任务。
 * 要把任务提交到这个池,必须创建RecursiveTask<R>的一个子类,其中R是并行化任务(以
 * 及所有子任务)产生的结果类型,或者如果任务不返回结果,则是RecursiveAction类型(当
 * 然它可能会更新其他非局部机构)。要定义 RecursiveTask, 只需实现它唯一的抽象方法
 * compute:
 * protected abstract R compute();
 * 这个方法同时定义了将任务拆分成子任务的逻辑,以及无法再拆分或不方便再拆分时,生成
 * 单个子任务结果的逻辑。
 */
public class ForkJoinSumCalculator extends RecursiveTask<Long> {
    public static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();
    public static final long THRESHOLD = 10_000;

    private final long[] numbers;
    private final int start;
    private final int end;

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            return computeSequentially();
        }
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
        leftTask.fork();
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);
        Long rightResult = rightTask.compute();
        Long leftResult = leftTask.join();
        return leftResult + rightResult;
    }

    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return FORK_JOIN_POOL.invoke(task);
    }
}

