package com.uj.study.concurrency.shopprice.v2.async;

import java.util.concurrent.Future;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/31 下午2:17
 * @description：
 */
public class AsyncShopClient {
    public static void main(String[] args) {
        AsyncShop shop = new AsyncShop("BestShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPrice("myPhone");
        long incocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Invocation returned after " + incocationTime + " msecs");
        try {
            System.out.println("Price is " + futurePrice.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        long retrivalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + retrivalTime + " msecs");
    }
}
