package com.uj.study.design.patterns.observer;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/24 下午5:53
 * @description：
 */
public class MainLambda {
    public static void main(String[] args) {
        //old school
        Feed f = new Feed();
        f.registerObserver(new NYTimes());
        f.registerObserver(new Guardian());
        f.registerObserver(new LeMonde());
        f.notifyObservers("The queen said her favourite book is Java 8 in Action!");


        //lambda
        Feed feedLambda = new Feed();
        feedLambda.registerObserver((String tweet) -> {
            if(tweet != null && tweet.contains("money")){
                System.out.println("Breaking news in NY! " + tweet); }
        });
        feedLambda.registerObserver((String tweet) -> {
            if(tweet != null && tweet.contains("queen")){
                System.out.println("Yet another news in London... " + tweet); }
        });

        feedLambda.notifyObservers("Money money money, give me money!");
    }
}
