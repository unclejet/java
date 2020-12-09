package com.uj.study.design.patterns.observer.example2;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/9 下午12:15
 * @description：
 */
public class Aliens implements LandingObserver {

    @Override
    public void observeLanding(String name) {
        if (name.contains("Apollo")) {
            System.out.println("They're distracted, lets invade earth!");
        }
    }
}
