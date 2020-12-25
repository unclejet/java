package com.uj.study.optional.useOptional;

import java.util.Optional;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/23 下午12:40
 * @description：
 */
public class CombineTwoOptional {
    //old school
    public Insurance findCheapestInsurance(Person person, Car car) {
        Insurance cheapestCompany = null;
// 不同的保险公司提供的查询服务
// 对比所有数据
        return cheapestCompany;
    }

    public Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> person, Optional<Car> car) {
        if (person.isPresent() && car.isPresent()) {
            return Optional.of(findCheapestInsurance(person.get(), car.get()));
        } else {
            return Optional.empty();
        }
    }

    /**
     * 结合本节中介绍的map和flatMap方法,用一行语句重新实现之前出现的nullSafeFind-
     * CheapestInsurance()方法。
     * 这段代码中,你对第一个Optional对象调用 flatMap方法,如果它是个空值,传递给它
     * 的Lambda表达式不会执行,这次调用会直接返回一个空的Optional对象。反之,如果person
     * 对象存在,这次调用就会将其作为函数Function的输入,并按照与flatMap方法的约定返回
     * 一个Optional<Insurance>对象。这个函数的函数体会对第二个Optional 对象执行map操
     * 作,如果第二个对象不包含 car ,函数 Function 就返回一个空的 Optional 对象,整个
     * nullSafeFindCheapestInsuranc方法的返回值也是一个空的Optional对象。最后,如果
     * person 和car对象都存在,作为参数传递给map方法的Lambda表达式能够使用这两个值安全
     * 地调用原始的findCheapestInsurance方法,完成期望的操作。
     * @param person
     * @param car
     * @return
     */
    public Optional<Insurance> nullSafeFindCheapestInsurance1(Optional<Person> person, Optional<Car> car) {
        return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
    }

}
