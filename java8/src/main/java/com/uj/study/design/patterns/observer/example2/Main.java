package com.uj.study.design.patterns.observer.example2;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/9 下午12:16
 * @description：
 */
public class Main {
    public static void main(String[] args) {
        classBasedExample();
        lambdaBasedExample();
    }

    private static void classBasedExample() {
// BEGIN classBasedExample
        Moon moon = new Moon();
        moon.startSpying(new Nasa());
        moon.startSpying(new Aliens());

        moon.land("An asteroid");
        moon.land("Apollo 11");
// END classBasedExample
    }

    private static void lambdaBasedExample() {
// BEGIN lambdaBasedExample
        Moon moon = new Moon();

        moon.startSpying(name -> {
            if (name.contains("Apollo"))
                System.out.println("We made it!");
        });

        moon.startSpying(name -> {
            if (name.contains("Apollo"))
                System.out.println("They're distracted, lets invade earth!");
        });

        moon.land("An asteroid");
        moon.land("Apollo 11");
// END lambdaBasedExample
    }
}
