package com.uj.study.design.patterns.observer.example1;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/24 下午5:49
 * @description：
 */
public class Guardian implements Observer {
    @Override
    public void inform(String tweet) {
        if(tweet != null && tweet.contains("queen")){
            System.out.println("Yet another news in London... " + tweet);
        }
    }
}
