package com.uj.study.concurrency.shopprice.v2;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Stream;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/31 下午2:26
 * @description：
 * 本章你看到的所有示例代码都是通过在响应之前添加1秒钟的等待延迟模拟方法的远程调
 * 用。毫无疑问,现实世界中,你的应用访问各个远程服务时很可能遭遇无法预知的延迟,触发的
 * 原因多种多样,从服务器的负荷到网络的延迟,有些甚至是源于远程服务如何评估你应用的商业
 * 价值,即可能相对于其他的应用,你的应用每次查询的消耗时间更长。
 * 由于这些原因,你希望购买的商品在某些商店的查询速度要比另一些商店更快。
 */
public class OtherImportantFeature {
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
     * 目前为止,你实现的findPrices方法只有在取得所有商店的返回值时才显示商品的价格。
     * 而你希望的效果是,只要有商店返回商品价格就在第一时间显示返回值,不再等待那些还未返回
     * 的商店(有些甚至会发生超时)。你如何实现这种更进一步的改进要求呢?
     * @param product
     * @return
     */
    public Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote ->
                        CompletableFuture.supplyAsync(
                                () -> Discount.applyDiscount(quote), executor)));
    }

    /**
     * 由 于 thenAccept 方 法 已 经 定 义 了 如 何 处 理 CompletableFuture 返 回 的 结 果 , 一 旦
     * CompletableFuture计算得到结果,它就返回一个CompletableFuture<Void>。所以,map
     * 操作返回的是一个 Stream<CompletableFuture<Void>> 。对这个 <CompletableFuture-
     * <Void>>对象,你能做的事非常有限,只能等待其运行结束,不过这也是你所期望的。你还希望
     * 能给最慢的商店一些机会,让它有机会打印输出返回的价格。为了实现这一目的,你可以把构成
     * Stream的所有CompletableFuture<Void>对象放到一个数组中,等待所有的任务执行完成,
     * 代码如下所示。
     *
     * allOf工厂方法接收一个由CompletableFuture构成的数组,数组中的所有Completable-
     * Future 对象执行完成之后,它返回一个CompletableFuture<Void>对象。这意味着,如果你需
     * 要 等 待 最 初 Stream 中 的 所 有 CompletableFuture 对 象 执 行 完 毕 , 对 allOf 方 法 返 回 的
     * CompletableFuture 执行join 操作是个不错的主意。这个方法对“最佳价格查询器”应用也是
     * 有用的,因为你的用户可能会困惑是否后面还有一些价格没有返回,使用这个方法,你可以在执
     * 行完毕之后打印输出一条消息“All shops returned results or timed out”
     *  。
     * @param product
     * @return
     */
    public void findPricesStreamAllOf(String product) {
        CompletableFuture[] futures = findPricesStream("myPhone")
                .map(f -> f.thenAccept(System.out::println))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();
    }

    /**
     * 然而在另一些场景中,你可能希望只要CompletableFuture对象数组中有任何一个执行完
     * 毕就不再等待,比如,你正在查询两个汇率服务器,任何一个返回了结果都能满足你的需求。在
     * 这种情况下,你可以使用一个类似的工厂方法anyOf。该方法接收一个CompletableFuture对象
     * 构成的数组,返回由第一个执行完毕的CompletableFuture对象的返回值构成的Completable-
     * Future<Object>。
     * @param product
     */
    public void findPricesStreamAnyOf(String product) {
        CompletableFuture[] futures = findPricesStream("myPhone")
                .map(f -> f.thenAccept(System.out::println))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.anyOf(futures);
    }

    public static void main(String[] args) {
        OtherImportantFeature feature = new OtherImportantFeature();
        /**
         * 现在,你为findPricesStream 方法返回的Stream添加了第四个map操作,在此之前,你
         * 已 经 在 该 方 法 内 部 调 用 了 三 次 map 。 这 个 新 添 加 的 操 作 其 实 很 简 单 , 只 是 在 每 个
         * CompletableFuture上注册一个操作,该操作会在CompletableFuture 完成执行后使用它的
         * 返 回 值 。 Java 8 的 CompletableFuture 通 过 thenAccept 方 法 提 供 了 这 一 功 能 , 它 接 收
         * CompletableFuture执行完毕后的返回值做参数。在这里的例子中,该值是由 Discount服务
         * 返回的字符串值,它包含了提供请求商品的商店名称及折扣价格,你想要做的操作也很简单
         * 注意,和你之前看到的thenCompose 和thenCombine方法一样,thenAccept方法也提供
         * 了一个异步版本,名为thenAcceptAsync。异步版本的方法会对处理结果的消费者进行调度,
         * 从线程池中选择一个新的线程继续执行,不再由同一个线程完成CompletableFuture的所有任
         * 务。因为你想要避免不必要的上下文切换,更重要的是你希望避免在等待线程上浪费时间,尽快
         * 响应CompletableFuture的completion事件,所以这里没有采用异步版本。
         */
        feature.findPricesStream("myPhone").map(f -> f.thenAccept(System.out::println));
    }

}
