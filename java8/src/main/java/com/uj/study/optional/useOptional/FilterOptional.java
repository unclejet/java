package com.uj.study.optional.useOptional;

import java.util.Optional;
import java.util.Properties;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/24 上午11:47
 * @description：
 */
public class FilterOptional {
    public void oldSchool() {
        Insurance insurance = new Insurance();
        if (insurance != null && "CambridgeInsurance".equals(insurance.getName())) {
            System.out.println("ok");
        }
    }

    public void lambda() {
        Optional<Insurance> optInsurance = Optional.empty();
        optInsurance.filter(insurance ->
                "CambridgeInsurance".equals(insurance.getName()))
                .ifPresent(x -> System.out.println("ok"));
    }

    /**
     * 找出年龄大于或者等于minAge参数的Person所对应的保险公司列表
     * @param person
     * @param minAge
     * @return
     */
    public String getCarInsuranceName(Optional<Person> person, int minAge) {
        return person.filter(p -> p.getAge() >= minAge)
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }


    //example 2

    public static Optional<Integer> stringToInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public int readDurationOldSchool(Properties props, String name) {
        String value = props.getProperty(name);
        if (value != null) {
            try {
                int i = Integer.parseInt(value);
                if (i > 0) {
                    return i;
                }
            } catch (NumberFormatException nfe) { }
        }
        return 0;
    }

    public int readDuration(Properties props, String name) {
        return Optional.ofNullable(props.getProperty(name))
                .flatMap(FilterOptional::stringToInt)
                .filter(i -> i > 0)
                .orElse(0);
    }

}
