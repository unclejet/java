package com.uj.study.design.patterns.template.method.example1;

import java.util.function.Consumer;

/**
 * @author ：UncleJet
 * @date ：Created in 2020/11/24 下午12:55
 * @description：
 */
public class OnlineBankingLambda {
    public static void main(String[] args) {
        new OnlineBankingLambda().processCustomer(1337, (Customer c) -> System.out.println("Hello!"));
    }

    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy.accept(c);
    }

    // dummy Customer class
    static private class Customer {
    }

    // dummy Database class
    static private class Database {
        static Customer getCustomerWithId(int id) {
            return new Customer();
        }
    }
}
