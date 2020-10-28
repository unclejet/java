package com.uj.study.collectors;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

/**
 * @author ：unclejet
 * @date ：Created in 2020/10/27 下午5:42
 * @description：
 * Collector接口包含了一系列方法, 为实现具体的归约操作(即收集器)提供了范本。我们
 * 已经看过了Collector接口中实现的许多收集器,例如 toList或groupingBy。这也意味着,
 * 你可以为Collector接口提供自己的实现,从而自由地创建自定义归约操作。
 * @modified By：
 * @version:
 */
public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

    /**
     * 1. 建立新的结果容器:supplier方法
     * supplier方法必须返回一个结果为空的Supplier,也就是一个无参数函数,在调用时它会
     * 创建一个空的累加器实例,供数据收集过程使用。
     * @return
     */
    @Override
    public Supplier<List<T>> supplier() {
        return () -> new ArrayList<T>();
    }

    /**
     * 2. 将元素添加到结果容器:accumulator方法
     * accumulator方法会返回执行归约操作的函数。当遍历到流中第n个元素时,这个函数执行
     * 时会有两个参数:保存归约结果的累加器(已收集了流中的前 n1 个项目)
     *  ,还有第n个元素本身。
     * 该函数将返回void,因为累加器是原位更新,即函数的执行改变了它的内部状态以体现遍历的
     * 元素的效果。
     * @return
     */
    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return (list, item) -> list.add(item);
    }

    /**
     * 3. 对结果容器应用最终转换:finisher方法
     * 在遍历完流后,finisher方法必须返回在累积过程的最后要调用的一个函数,以便将累加
     * 器对象转换为整个集合操作的最终结果。通常,就像 ToListCollector的情况一样,累加器对
     * 象恰好符合预期的最终结果,因此无需进行转换。
     * @return
     */
    @Override
    public Function<List<T>, List<T>> finisher() {
        return i -> i;
    }

    /**
     * 4. 合并两个结果容器:combiner方法
     * 四个方法中的最后一个——combiner方法会返回一个供归约操作使用的函数,它定义了对
     * 流的各个子部分进行并行处理时,各个子部分归约所得的累加器要如何合并。对于toList而言,
     * 这个方法的实现非常简单,只要把从流的第二个部分收集到的项目列表加到遍历第一部分时得到
     * 的列表后面就行了
     * @return
     */
    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    /**
     * 5. characteristics方法
     * 最后一个方法——characteristics会返回一个不可变的Characteristics集合,它定义
     * 了 收 集 器的行 为 ——尤其 是 关 于流是 否 可 以并行 归 约 ,以及 可 以 使用哪 些 优 化的提 示 。
     * Characteristics是一个包含三个项目的枚举。
     *  UNORDERED——归约结果不受流中项目的遍历和累积顺序的影响。
     *  CONCURRENT——accumulator函数可以从多个线程同时调用,且该收集器可以并行归
     * 约流。如果收集器没有标为UNORDERED,那它仅在用于无序数据源时才可以并行归约。
     *  IDENTITY_FINISH——这表明完成器方法返回的函数是一个恒等函数,可以跳过。这种
     * 情况下,累加器对象将会直接用作归约过程的最终结果。这也意味着,将累加器A不加检
     * 查地转换为结果R是安全的。
     * @return
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
    }
}

