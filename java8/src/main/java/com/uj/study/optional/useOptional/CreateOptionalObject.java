package com.uj.study.optional.useOptional;

import java.util.Optional;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/23 上午11:38
 * @description：
 */
public class CreateOptionalObject {
    public static void main(String[] args) {
        //声明一个空的Optional
        Optional<Car> optCar = Optional.empty();

        Car car = new Car();
        //依据一个非空值创建Optional
        Optional<Car> optCar1 = Optional.of(car);

        //可接受null的 Optional
        Optional<Car> optCar2 = Optional.ofNullable(car);


    }
}
