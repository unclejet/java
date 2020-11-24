package com.uj.study.design.patterns.observer;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/24 下午5:37
 * @description：
 */
public interface Subject {
    void registerObserver(Observer o);
    void notifyObservers(String tweet);
}
