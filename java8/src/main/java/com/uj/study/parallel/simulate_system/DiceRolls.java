package com.uj.study.parallel.simulate_system;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/19 下午6:09
 * @description：
 * 并行化流操作的用武之地是使用简单操作处理大量数据, 比如模拟系统。本节我们会搭建
 * 一个简易的模拟系统来理解摇骰子,但其中的原理对于大型、真实的系统也适用。
 * 我们这里要讨论的是蒙特卡洛模拟法。蒙特卡洛模拟法会重复相同的模拟很多次,每次模
 * 拟都使用随机生成的种子。每次模拟的结果都被记录下来,汇总得到一个对系统的全面模
 * 拟。蒙特卡洛模拟法被大量用在工程、金融和科学计算领域。
 * 如果公平地掷两次骰子,然后将赢的一面上的点数相加,就会得到一个 2~12 的数字。点
 * 数的和至少是 2,因为骰子六个面上最小的点数是 1,而我们将骰子掷了两次;点数的和
 * 最大超不过 12,因为骰子点数最多的一面也不过 6 点。我们想要得出点数落在 2~12 之间
 * 每个值的概率。
 * 解决该问题的方法之一是求出掷骰子的所有组合,比如,得到 2 点的方式是第一次掷得 1
 * 点,第二次也掷得 1 点。总共有 36 种可能的组合,因此,掷得 2 点的概率就是 1/36。
 * 另外一种解法是使用 1 到 6 的随机数模拟掷骰子事件,然后用得到每个点数的次数除以总
 * 的投掷次数。这就是一个简单的蒙特卡洛模拟。模拟投掷骰子的次数越多,得到的结果越
 * 准确,因此,我们希望尽可能多地增加模拟次数。
 * 例 6-3 展示了如何使用流实现蒙特卡洛模拟法。N 代表模拟次数,在➊ 处使用 IntStream
 * 的 range 方 法 创 建 大 小 为 N 的 流, 在 ➋ 处 调 用 parallel 方 法 使 用 流 的 并 行 化 操 作,
 * twoDiceThrows 函数模拟了连续掷两次骰子事件,返回值是两次点数之和。在 ➌ 处使用
 * mapToObj 方法以便在流上使用该函数。
 */
public class DiceRolls {

    private static final int N = 100000000;

//    public static void main(String[] ignore) throws IOException, RunnerException {
//        final String[] args = {
//                ".*DiceRolls.*",
//                "-wi",
//                "5",
//                "-i",
//                "5"
//        };
//        Main.main(args);
//    }

    // BEGIN serial
    public Map<Integer, Double> serialDiceRolls() {
        double fraction = 1.0 / N;
        return IntStream.range(0, N)
                .mapToObj(twoDiceThrows())
                .collect(groupingBy(side -> side, summingDouble(n -> fraction)));
    }
    // END serial

    // BEGIN parallel
    public Map<Integer, Double> parallelDiceRolls() {
        double fraction = 1.0 / N;
        return IntStream.range(0, N)                        // <1>
                .parallel()                         // <2>
                .mapToObj(twoDiceThrows())          // <3>
                .collect(groupingBy(side -> side,   // <4>
                        summingDouble(n -> fraction))); // <5>
    }
    // END parallel

    private static IntFunction<Integer> twoDiceThrows() {
        return i -> {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            int firstThrow = random.nextInt(1, 7);
            int secondThrow = random.nextInt(1, 7);
            return firstThrow + secondThrow;
        };
    }

}
