package com.uj.study.optional.useOptional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/23 上午11:58
 * @description：
 */
public class FlatMapOptional {
    public String oldSchool(Person person) {
        return person.getCarObj().getInsuranceObj().getName();
    }

    /**
     * 无法通过编译
     * @param person
     */
    public void optionalMethod(Person person) {
        Optional<Person> optPerson = Optional.of(person);
        //无法通过编译
//        Optional<String> name =
//                optPerson.map(Person::getCar)
//                        .map(Car::getInsurance)
//                        .map(Insurance::getName);
    }

    /**
     * flatMap
     * @param person
     * @return
     */
    public String getCarInsuranceName(Optional<Person> person) {
        return person.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }

    public Set<String> getCarInsuranceNames(List<Person> persons) {
        return persons.stream()
                .map(Person::getCar)
                .map(optCar -> optCar.flatMap(Car::getInsurance))
                .map(optInsurance -> optInsurance.map(Insurance::getName))
                .flatMap(Optional::stream)
                .collect(toSet());
    }

}
