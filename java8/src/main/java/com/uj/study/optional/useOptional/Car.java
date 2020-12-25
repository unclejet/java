package com.uj.study.optional.useOptional;

import java.util.Optional;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/23 上午11:30
 * @description：
 */
public class Car {
    private Insurance insuranceObj;
    private Optional<Insurance> insurance;

    public Optional<Insurance> getInsurance() {
        return insurance;
    }

    public Insurance getInsuranceObj() {
        return insuranceObj;
    }
}
