package com.uj.study.optional;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/12/22 下午12:49
 * @description：
 */
public class HowToHandleNullBad {
    /**
     * 有抛nullpointerexception的风险
     * @param person
     * @return
     */
    public String getCarInsuranceName(Person person) {
        return person.getCar().getInsurance().getName();
    }

    /**
     * 采用防御式检查减少 NullPointerException
     * @param person
     * @return
     */
    public String getCarInsuranceName1(Person person) {
        if (person != null) {
            Car car = person.getCar();
            if (car != null) {
                Insurance insurance = car.getInsurance();
                if (insurance != null) {
                    return insurance.getName();
                }
            }
        }
        return "Unknown";
    }

    /**
     * 过多的退出语句
     * @param person
     * @return
     */
    public String getCarInsuranceName2(Person person) {
        if (person == null) {
            return "Unknown";
        }
        Car car = person.getCar();
        if (car == null) {
            return "Unknown";
        }
        Insurance insurance = car.getInsurance();
        if (insurance == null) {
            return "Unknown";
        }
        return insurance.getName();
    }


    public class Person {
        private Car car;
        public Car getCar() { return car; }
    }
    public class Car {
        private Insurance insurance;
        public Insurance getInsurance() { return insurance; }
    }
    public class Insurance {
        private String name;
        public String getName() { return name; }
    }

}
