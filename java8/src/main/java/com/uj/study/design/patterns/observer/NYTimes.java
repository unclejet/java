package com.uj.study.design.patterns.observer;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/24 下午5:46
 * @description：
 */
public class NYTimes implements Observer {
    @Override
    public void inform(String tweet) {
        if(tweet != null && tweet.contains("money")){
            System.out.println("Breaking news in NY!" + tweet);
        }
    }
}