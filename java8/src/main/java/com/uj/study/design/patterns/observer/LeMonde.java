package com.uj.study.design.patterns.observer;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/24 下午5:51
 * @description：
 */
public class LeMonde implements Observer{
    @Override
    public void inform(String tweet) {
        if(tweet != null && tweet.contains("wine")){
            System.out.println("Today cheese, wine and news! " + tweet);
        }
    }
}
