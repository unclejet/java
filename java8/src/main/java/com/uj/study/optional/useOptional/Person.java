package com.uj.study.optional.useOptional;

import java.util.Optional;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/23 上午11:31
 * @description：
 */
public class Person {
    private Car carObj;
    private Optional<Car> car;

    public Optional<Car> getCar() {
        return car;
    }

    public Car getCarObj() {
        return carObj;
    }

    public int getAge() {
        return 0;
    }
}
