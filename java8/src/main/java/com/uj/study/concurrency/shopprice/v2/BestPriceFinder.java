package com.uj.study.concurrency.shopprice.v2;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/31 上午11:52
 * @description：不但要远程查价格(price)，还要远程调用折扣服务(discount)
 */
public class BestPriceFinder {
    private final List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("ShopEasy"));

    private final Executor executor = Executors.newFixedThreadPool(shops.size(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });

    /**
     * 以最简单的方式实现使用 Discount服务的findPrices方法
     * 通过在shop构成的流上采用流水线方式执行三次map操作,我们得到了期望的结果。
     *  第一个操作将每个shop对象转换成了一个字符串,该字符串包含了该 shop中指定商品的
     * 价格和折扣代码。
     *  第二个操作对这些字符串进行了解析,在Quote对象中对它们进行转换。
     *  最终,第三个map会操作联系远程的Discount服务,计算出最终的折扣价格,并返回该
     * 价格及提供该价格商品的shop。
     * @param product
     * @return
     */
    public List<String> findPricesSequential(String product) {
        return shops.stream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    public List<String> findPricesParallel(String product) {
        return shops.parallelStream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    /**
     * 为了实现这一目标,你像第一个调用传递 getPrice 给 supplyAsync 那样,将这一操作以
     * Lambda表达式的方式传递给了supplyAsync 工厂方法,该方法最终会返回另一个Completable-
     * Future对象。到目前为止,你已经进行了两次异步操作,用了两个不同的CompletableFutures
     * 对象进行建模,你希望能把它们以级联的方式串接起来进行工作。
     *  从shop 对象中获取价格,接着把价格转换为Quote。
     *  拿到返回的Quote对象,将其作为参数传递给Discount服务,取得最终的折扣价格。
     * Java 8的 CompletableFuture API提供了名为thenCompose的方法,它就是专门为这一目
     * 的而设计的,thenCompose方法允许你对两个异步操作进行流水线,第一个操作完成时,将其
     * 结果作为参数传递给第二个操作。换句话说,你可以创建两个CompletableFutures对象,对
     * 第 一 个 CompletableFuture 对 象 调 用 thenCompose , 并 向 其 传 递 一 个 函 数 。 当 第 一 个
     * CompletableFuture执行完毕后,它的结果将作为该函数的参数,这个函数的返回值是以第一
     * 个CompletableFuture的返回做输入计算出的第二个 CompletableFuture对象。使用这种方
     * 式,即使Future在向不同的商店收集报价,主线程还是能继续执行其他重要的操作,比如响应
     * UI事件。
     * 将这三次map 操作的返回的Stream元素收集到一个列表,你就得到了一个List<Comple-
     * tableFuture<String>>,等这些CompletableFuture对象最终执行完毕,你就可以像代码清
     * 单11-11中那样利用join取得它们的返回值。
     *
     * 你对一个CompletableFuture 对象调用了thenCompose 方法,并向其
     * 传 递 了 第 二 个 CompletableFuture , 而 第 二 个 CompletableFuture 又 需 要 使 用 第 一 个
     * CompletableFuture的执行结果作为输入。但是,另一种比较常见的情况是,你需要将两个完
     * 全不相干的CompletableFuture对象的结果整合起来,而且你也不希望等到第一个任务完全结
     * 束才开始第二项任务。
     * @param product
     * @return
     */
    public List<String> findPricesFuture(String product) {
        List<CompletableFuture<String>> priceFutures = findPricesStream(product)
                .collect(Collectors.<CompletableFuture<String>>toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)));
    }

    /**
     * 合并两个独立的CompletableFuture对象
     * 这种情况,你应该使用thenCombine方法,它接收名为BiFunction的第二参数,这个参数
     *      * 定义了当两个CompletableFuture对象完成计算后,结果如何合并。同thenCompose方法一样,
     *      * thenCombine 方法也提供有一个 Async 的版本。这里,如果使用 thenCombineAsync 会导致
     *      * BiFunction中定义的合并操作被提交到线程池中,由另一个任务以异步的方式执行。
     *      这里整合的操作只是简单的乘法操作,用另一个单独的任务对其进行操作有些浪费资源,所
     * 以你只要使用thenCombine方法,无需特别求助于异步版本的thenCombineAsync方法。
     */
    public void thenCombine() {
//        Future<Double> futurePriceInUSD =
//                CompletableFuture.supplyAsync(() -> shop.getPrice(product))
//                .thenCombine(
//                        CompletableFuture.supplyAsync(
//                                        () -> exchangeService.getRate(Money.EUR, Money.USD)),
//                (price, rate) -> price * rate;);
    }

    public void printPricesStream(String product) {
        long start = System.nanoTime();
        CompletableFuture[] futures = findPricesStream(product)
                .map(f -> f.thenAccept(s -> System.out.println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();
        System.out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }
}
