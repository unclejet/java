package com.uj.study.concurrency.shopprice.v1;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.uj.study.concurrency.shopprice.Util.delay;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/28 上午11:55
 * @description：
 */
public class Shop {
    private final String name;
    private final Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    /**
     * 最慢的，无法接受，改为调用getPriceAsync方法
     * @param product
     * @return
     */
    private double calculatePrice(String product) {
        delay(); //模拟耗时操作
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    /**
     * 改为异步的
     * @param product
     * @return
     */
    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread( () -> {
            double price = calculatePrice(product);
            futurePrice.complete(price);
        }).start();
        return futurePrice;
    }

    /**
     * 使用工厂方法supplyAsync创建CompletableFuture
     * 目前为止我们已经了解了如何通过编程创建CompletableFuture对象以及如何获取返回
     * 值,虽然看起来这些操作已经比较方便,但还有进一步提升的空间,CompletableFuture类自
     * 身提供了大量精巧的工厂方法,使用这些方法能更容易地完成整个流程,还不用担心实现的细节。
     * 比如,采用supplyAsync方法后,你可以用一行语句重写上面代码
     *
     * supplyAsync 方法接受一个生产者(Supplier)作为参数,返回一个CompletableFuture
     * 对象,该对象完成异步执行后会读取调用生产者方法的返回值。生产者方法会交由ForkJoinPool
     * 池中的某个执行线程(Executor)运行,但是你也可以使用supplyAsync方法的重载版本,传
     * 递第二个参数指定不同的执行线程执行生产者方法。
     *
     * 本方法返回的 CompletableFuture 对象和代码getPriceAsyncHandleException中你手工创建和完成的CompletableFuture对象是完全等价的,这意味着它提供了同样的
     * 错误管理机制,而前者你花费了大量的精力才得以构建。
     * @param product
     * @return
     */
    public Future<Double> getPriceAsyncMoreSmart(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    /**
     * 客户端可以使用重载版本的get方法,它使用一个超时参数来避免发生这样的情况。这是一
     * 种值得推荐的做法,你应该尽量在你的代码中添加超时判断的逻辑,避免发生类似的问题。使用
     * 这种方法至少能防止程序永久地等待下去,超时发生时,程序会得到通知发生了 Timeout-
     * Exception。不过,也因为如此,你不会有机会发现计算商品价格的线程内到底发生了什么问题
     * 才引发了这样的失效。为了让客户端能了解商店无法提供请求商品价格的原因,你需要使用
     * CompletableFuture的 completeExceptionally 方法将导致CompletableFuture内发生问
     * 题的异常抛出。
     *
     * 客户端现在会收到一个ExecutionException 异常,该异常接收了一个包含失败原因的
     * Exception参数,即价格计算方法最初抛出的异常。
     * @param product
     * @return
     */
    public Future<Double> getPriceAsyncHandleException(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread( () -> {
            try {
                double price = calculatePrice(product);
                futurePrice.complete(price);
            } catch (Exception ex) {
                futurePrice.completeExceptionally(ex);
            }
        }).start();
        return futurePrice;
    }



    public String getName() {
        return name;
    }

    public static void main(String[] args) {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime
                + " msecs");
        // Do some more tasks, like querying other shops
        doSomethingElse();
        // while the price of the product is being calculated
        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f%n", price);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");
    }

    private static void doSomethingElse() {
        System.out.println("Doing something else...");
    }

}
