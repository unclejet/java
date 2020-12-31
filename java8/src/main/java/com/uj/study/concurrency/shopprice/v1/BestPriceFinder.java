package com.uj.study.concurrency.shopprice.v1;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/29 上午11:58
 * @description：
 * 并行——使用流还是CompletableFutures?
 * 目前为止,你已经知道对集合进行并行计算有两种方式:要么将其转化为并行流,利用 map
 * 这样的操作开展工作,要么枚举出集合中的每一个元素,创建新的线程,在Completable-
 * Future内对其进行操作。后者提供了更多的灵活性,你可以调整线程池的大小,而这能帮助
 * 你确保整体的计算不会因为线程都在等待I/O而发生阻塞。
 * 我们对使用这些API的建议如下。
 * ❑如果你进行的是计算密集型的操作,并且没有I/O,那么推荐使用Stream接口,因为实
 * 现简单,同时效率也可能是最高的(如果所有的线程都是计算密集型的,那就没有必要
 * 创建比处理器核数更多的线程)。
 * ❑反之,如果你并行的工作单元还涉及等待I/O的操作(包括网络连接等待),那么使用
 * CompletableFuture 灵活性更好,你可以像前文讨论的那样,依据等待/计算,或者
 * W/C的比率设定需要使用的线程数。这种情况不使用并行流的另一个原因是,处理流的
 * 流水线中如果发生I/O等待,流的延迟特性会让我们很难判断到底什么时候触发了等待。
 */
public class BestPriceFinder {
    private final List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll")/*,
                                                   new Shop("ShopEasy")*/);

    /**
     *  采用顺序查询所有商店的方式实现的findPrices方法
     * @param product
     * @return
     */
    public List<String> findPricesSequential(String product) {
        return shops.stream()
                .map(shop -> shop.getName() + " price is " + shop.getPrice(product))
                .collect(Collectors.toList());
    }

    /**
     * 对findPrices进行并行操作
     * @param product
     * @return
     */
    public List<String> findPricesParallel(String product) {
        return shops.parallelStream()
                .map(shop -> shop.getName() + " price is " + shop.getPrice(product))
                .collect(Collectors.toList());
    }

    private final Executor executor = Executors.newFixedThreadPool(shops.size(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });

    /**
     * 使用CompletableFuture实现findPrices方法
     * 使 用这种 方式 ,你会 得到 一个 List<CompletableFuture<String>> , 列 表中的 每个
     * CompletableFuture 对象在计算完成后都包含商店的 String 类型的名称。但是,由于你用
     * CompletableFutures实现的findPrices方法要求返回一个List<String>,你需要等待所有
     * 的future执行完毕,将其包含的值抽取出来,填充到列表中才能返回。
     * 为了实现这个效果,你可以向最初的 List<CompletableFuture<String>> 施加第二个
     * map操作,对List中的所有future对象执行join操作,一个接一个地等待它们运行结束。注意
     * CompletableFuture 类中的 join 方法和 Future 接口中的 get 有相同的含义,并且也声明在
     * Future 接口中,它们唯一的不同是join 不会抛出任何检测到的异常。使用它你不再需要使用
     * try/catch语句块让你传递给第二个map方法的Lambda表达式变得过于臃肿。所有这些整合在一
     * 起,你就可以重新实现findPrices了,
     *
     * CompletableFuture版本的程序似乎比并行流版本的程序还快那么一点儿。但是最后这个
     * 版本也不太令人满意。比如,如果你试图让你的代码处理9个商店,并行流版本耗时3143毫秒,
     * 而CompletableFuture版本耗时3009毫秒。它们看起来不相伯仲,究其原因都一样:它们内部
     * 采用的是同样的通用线程池,默认都使用固定数目的线程,具体线程数取决于 Runtime.
     * getRuntime().availableProcessors()的返回值。然而, CompletableFuture具有一定的
     * 优势,因为它允许你对执行器(Executor)进行配置,尤其是线程池的大小,让它以更适合应
     * 用需求的方式进行配置,满足程序的要求,而这是并行流API无法提供的。
     *
     * 。不过,为了避免发生由于商店的数目过
     * 多导致服务器超负荷而崩溃,你还是需要设置一个上限,比如100个线程。
     * private final Executors.newFixedThreadPool(Math.min(shops.size(), Executor executor =
     *  100),
     * new ThreadFactory() {
     * public Thread newThread(Runnable r) {
     * Thread t = new Thread(r);
     * t.setDaemon(true);
     * return t;
     * });
     *
     * @param product
     * @return
     */
    public List<String> findPricesFuture(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price is "
                                + shop.getPrice(product), executor))
                        .collect(Collectors.toList());

        List<String> prices = priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        return prices;
    }

}
