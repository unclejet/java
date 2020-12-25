package com.uj.study.optional.useOptional;

import java.util.Optional;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/23 上午11:48
 * @description：
 */
public class OptionalMap {
    private Insurance insurance;

    public void oldSchool() {
        String name = null;
        if(insurance != null){
            name = insurance.getName();
        }
    }

    public void optionalMethod() {
        Optional<Insurance> optInsurance = Optional.ofNullable(insurance);
        Optional<String> name = optInsurance.map(Insurance::getName);
    }



}
